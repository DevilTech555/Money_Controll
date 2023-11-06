package com.dk24.moneycontrol.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.viewmodels.MonthlyGoalsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MonthlyGoalsViewCompose(drawerState: DrawerState) {

    val viewModel = viewModel<MonthlyGoalsViewModel>()

    Scaffold(
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        floatingActionButton = {
            CircleFabButton(contentDescription = "Add a goal") {
                viewModel.isAddGoalDialogVisible.value = true
            }
        },
        content = { innerPadding ->

            if (viewModel.needRefresh.value) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    viewModel.getGoals().groupBy { it.isAchieved }.toSortedMap(compareBy { it })
                        .forEach { (isAchieved, contactsForInitial) ->

                            stickyHeader {
                                MonthlyGoalsStickyHeader(if (isAchieved == true) "Achieved" else "Non-Achieved")
                            }
                            item {
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                            items(contactsForInitial) { goal: MonthlyGoals ->

                                GoalsListItem(monthlyGoals = goal, onCheckedChange = {
                                    goal.apply {
                                        this.isAchieved = it
                                        viewModel.updateGoal(this)
                                    }
                                }, onDelete = {
                                    viewModel.selectedGoal = it
                                    viewModel.isDeleteGoalDialogVisible.value = true
                                }, onEdit = {
                                    viewModel.selectedGoal = it
                                    viewModel.isEditGoalDialogVisible.value = true
                                })

                            }
                            item {
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }

                    item {
                        Spacer(modifier = Modifier.height(Constants.ScrollOffset))
                    }
                }
            }

            if (viewModel.isAddGoalDialogVisible.value) {
                AddGoalDialogCompose(onDismissRequest = {
                    viewModel.isAddGoalDialogVisible.value = false
                }, onAdd = { value ->
                    viewModel.addGoal(value)
                })
            }

            if (viewModel.isEditGoalDialogVisible.value) {
                viewModel.selectedGoal?.let {
                    UpdateGoalDialogCompose(
                        monthlyGoal = it,
                        onDismissRequest = {
                            viewModel.isEditGoalDialogVisible.value = false
                        }, onUpdate = { value ->
                            viewModel.updateGoal(value)
                        })
                } ?: run {
                    viewModel.isEditGoalDialogVisible.value = false
                }
            }

            if (viewModel.isDeleteGoalDialogVisible.value) {
                viewModel.selectedGoal?.let {
                    DeleteGoalDialogCompose(
                        monthlyGoal = it,
                        onDismissRequest = {
                            viewModel.isDeleteGoalDialogVisible.value = false
                        },
                        onDelete = { value ->
                            viewModel.removeGoal(value)
                        }
                    )
                } ?: run {
                    viewModel.isDeleteGoalDialogVisible.value = false
                }
            }
        }
    )
}
