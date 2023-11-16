package com.dk24.moneycontrol.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk24.moneycontrol.db.room.database.MoneyControlDatabase
import com.dk24.moneycontrol.db.room.model.MonthlyGoal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MonthlyGoalsViewModel : ViewModel() {

    private var database: MoneyControlDatabase = MoneyControlDatabase.getInstance()
    val goalsFlow = database.monthlyGoalDao().getAllMonthlyGoalsAsFlow()
    var selectedGoal: MonthlyGoal? = null

    fun addGoal(goal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            MonthlyGoal(
                description = goal,
                date = System.currentTimeMillis(),
                isAchieved = false
            ).also {
                database.monthlyGoalDao().insert(it)
            }
        }
    }

    fun updateGoal(monthlyGoal: MonthlyGoal?) {
        monthlyGoal?.let {
            viewModelScope.launch(Dispatchers.IO) {
                database.monthlyGoalDao().delete(monthlyGoal)
                launch {
                    database.monthlyGoalDao().insert(monthlyGoal)
                }
            }
        }
    }

    fun removeGoal(monthlyGoal: MonthlyGoal?) {
        monthlyGoal?.let {
            viewModelScope.launch(Dispatchers.IO) {
                database.monthlyGoalDao().delete(monthlyGoal)
            }
        }
    }
}
