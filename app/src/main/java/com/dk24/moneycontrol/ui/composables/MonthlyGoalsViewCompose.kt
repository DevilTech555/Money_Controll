package com.dk24.moneycontrol.ui.composables

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.enums.DBOperationType
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.viewmodels.MonthlyGoalsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MonthlyGoalsViewCompose(drawerState: DrawerState) {

    val viewModel = viewModel<MonthlyGoalsViewModel>()
    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(bg),
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        floatingActionButton = {
            CircleFabButton(contentDescription = stringResource(id = R.string.fab_cd_add_month_goal)) {
                viewModel.isAddGoalDialogVisible.value = true
            }
        },
        content = { innerPadding ->

            if (viewModel.needRefresh.value) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {

                    viewModel.getGoals().groupBy { it.isAchieved }.toSortedMap(compareBy { it })
                        .forEach { (isAchieved, contactsForInitial) ->

                            stickyHeader {
                                MonthlyGoalsStickyHeader(
                                    if (isAchieved == true) stringResource(id = R.string.achieved) else stringResource(
                                        id = R.string.non_achieved
                                    )
                                )
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
                                }, onChange = { monthlyGoals, dbOperationType ->
                                    when (dbOperationType) {
                                        DBOperationType.DELETE -> {
                                            viewModel.selectedGoal = monthlyGoals
                                            viewModel.isDeleteGoalDialogVisible.value = true
                                        }

                                        DBOperationType.EDIT -> {
                                            viewModel.selectedGoal = monthlyGoals
                                            viewModel.isEditGoalDialogVisible.value = true
                                        }
                                    }
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
                        title = stringResource(R.string.warning),
                        message = stringResource(R.string.month_goal_delete_dialog_text),
                        monthlyGoal = it,
                        onDismissRequest = {
                            viewModel.isDeleteGoalDialogVisible.value = false
                        },
                        onDelete = { value ->
                            viewModel.removeGoal(value as MonthlyGoals)
                        }
                    )
                } ?: run {
                    viewModel.isDeleteGoalDialogVisible.value = false
                }
            }
        }
    )

    SetStatusBarColor(color = bg)
}
