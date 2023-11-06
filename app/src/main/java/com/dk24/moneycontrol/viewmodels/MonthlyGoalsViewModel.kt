package com.dk24.moneycontrol.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.db.store.ObjectBoxStore

class MonthlyGoalsViewModel : ViewModel() {

    private val monthlyGoalsBox = ObjectBoxStore.get().boxFor(MonthlyGoals::class.java)

    private val title = "Goals"

    val isDeleteGoalDialogVisible = mutableStateOf(false)
    val isEditGoalDialogVisible = mutableStateOf(false)
    val isAddGoalDialogVisible = mutableStateOf(false)
    val needRefresh = mutableStateOf(true)

    var selectedGoal: MonthlyGoals? = null

    fun getTitle() = title

    fun getGoals(): MutableList<MonthlyGoals> = monthlyGoalsBox.all

    fun addGoal(goal: String) {
        MonthlyGoals(
            description = goal,
            date = System.currentTimeMillis(),
            isAchieved = false
        ).also {
            if (goal.isNotBlank()) {
                monthlyGoalsBox.put(it)
            }
        }
        isAddGoalDialogVisible.value = false
    }

    fun updateGoal(monthlyGoals: MonthlyGoals) {
        monthlyGoalsBox.put(monthlyGoals)
        isEditGoalDialogVisible.value = false
        refresh()
    }

    fun removeGoal(monthlyGoals: MonthlyGoals) {
        monthlyGoalsBox.remove(monthlyGoals)
        isDeleteGoalDialogVisible.value = false
        refresh()
    }

    private fun refresh() {
        needRefresh.value = false
        needRefresh.value = true
    }
}
