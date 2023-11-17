package com.dk24.moneycontrol.ui.composables

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.model.MonthlyGoal
import com.dk24.moneycontrol.enums.DBOperationType
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.Constants.GOALS_VIEW_TITLE
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.utilites.showToast
import com.dk24.moneycontrol.viewmodels.MonthlyGoalsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthlyGoalsViewCompose(drawerState: DrawerState, context: Context) {

    val viewModel = viewModel<MonthlyGoalsViewModel>()
    val goalsList by viewModel.goalsFlow.collectAsState(initial = emptyList())

    var isDeleteGoalDialogVisible by remember {
        mutableStateOf(false)
    }
    var isEditGoalDialogVisible by remember {
        mutableStateOf(false)
    }
    var isAddGoalDialogVisible by remember {
        mutableStateOf(false)
    }

    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(bg),
        topBar = {
            TopBarCompose(
                title = GOALS_VIEW_TITLE,
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        floatingActionButton = {
            CircleFabButton(contentDescription = stringResource(id = R.string.fab_cd_add_month_goal)) {
                isAddGoalDialogVisible = true
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            goalsList
                .groupBy { it.isAchieved }
                .toSortedMap(compareBy { it })
                .forEach { (isAchieved, goalsList) ->

                    stickyHeader {
                        MonthlyGoalsStickyHeader(
                            if (isAchieved) stringResource(id = R.string.achieved) else stringResource(
                                id = R.string.non_achieved
                            )
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(goalsList.size) { index ->
                        goalsList[index].let { goal ->
                            GoalsListItem(monthlyGoal = goal, onCheckedChange = {
                                goal.apply {
                                    this.isAchieved = it
                                    viewModel.updateGoal(this)
                                }
                            }, onChange = { monthlyGoal, dbOperationType ->
                                when (dbOperationType) {
                                    DBOperationType.DELETE -> {
                                        viewModel.selectedGoal = monthlyGoal
                                        isDeleteGoalDialogVisible = true
                                    }

                                    DBOperationType.EDIT -> {
                                        viewModel.selectedGoal = monthlyGoal
                                        isEditGoalDialogVisible = true
                                    }
                                }
                            })
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

            item {
                Spacer(modifier = Modifier.height(Constants.ScrollOffset))
            }
        }

        if (isAddGoalDialogVisible) {
            AddGoalDialogCompose(onDismissRequest = {
                isAddGoalDialogVisible = false
            }, onAdd = { value ->
                if (value.isBlank()) {
                    context.showToast("Goal is blank..!")
                } else {
                    viewModel.addGoal(value)
                }
                isAddGoalDialogVisible = false
            })
        }

        if (isEditGoalDialogVisible) {
            UpdateGoalDialogCompose(
                monthlyGoal = viewModel.selectedGoal,
                onDismissRequest = {
                    isEditGoalDialogVisible = false
                }, onUpdate = { value ->
                    viewModel.updateGoal(value)
                    isEditGoalDialogVisible = false
                })
        }

        if (isDeleteGoalDialogVisible) {
            DeleteGoalDialogCompose(
                title = stringResource(R.string.warning),
                message = stringResource(R.string.month_goal_delete_dialog_text),
                monthlyGoal = viewModel.selectedGoal,
                onDismissRequest = {
                    isDeleteGoalDialogVisible = false
                },
                onDelete = { value ->
                    if (value is MonthlyGoal) {
                        viewModel.removeGoal(value)
                        isDeleteGoalDialogVisible = false
                    }
                }
            )
        }
    }

    SetStatusBarColor(color = bg)
}
