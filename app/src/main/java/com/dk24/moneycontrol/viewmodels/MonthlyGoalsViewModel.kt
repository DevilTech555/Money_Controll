package com.dk24.moneycontrol.viewmodels

import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.db.objectbox.entities.MonthlyGoals
import com.dk24.moneycontrol.db.objectbox.store.ObjectBoxStore

class MonthlyGoalsViewModel : ViewModel() {

    private val title = "Goals"

    val goalsFlow = ObjectBoxStore.getAllMonthlyGoalsAsFlow()

    var selectedGoal: MonthlyGoals? = null

    fun getTitle() = title

    fun addGoal(goal: String) = ObjectBoxStore.insertMonthlyGoal(goal)

    fun updateGoal(monthlyGoals: MonthlyGoals?) = ObjectBoxStore.updateMonthlyGoal(monthlyGoals)

    fun removeGoal(monthlyGoals: MonthlyGoals?) = ObjectBoxStore.removeMonthlyGoal(monthlyGoals)
}
