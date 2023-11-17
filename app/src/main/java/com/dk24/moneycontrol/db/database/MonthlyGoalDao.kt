package com.dk24.moneycontrol.db.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dk24.moneycontrol.db.model.MonthlyGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyGoalDao {
    @Insert
    suspend fun insert(monthlyGoal: MonthlyGoal)

    @Delete
    suspend fun delete(monthlyGoal: MonthlyGoal)

    @Query("SELECT * FROM Monthly_Goal")
    fun getAllMonthlyGoals(): List<MonthlyGoal>

    @Query("SELECT * FROM Monthly_Goal")
    fun getAllMonthlyGoalsAsFlow(): Flow<List<MonthlyGoal>>

    @Query("SELECT * FROM Monthly_Goal WHERE id = :id")
    fun getMonthlyGoalById(id: Long): Flow<MonthlyGoal>
}
