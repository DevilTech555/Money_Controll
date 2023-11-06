package com.dk24.moneycontrol.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.db.store.ObjectBoxStore

class MonthlyGoalsViewModel : ViewModel() {

    private val monthlyGoalsBox = ObjectBoxStore.get().boxFor(MonthlyGoals::class.java)

    private val title = "Goals"
    val isAddGoalDialogVisible = mutableStateOf(false)

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
}
