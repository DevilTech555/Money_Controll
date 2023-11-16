package com.dk24.moneycontrol.db.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dk24.moneycontrol.db.room.model.MonthlyGoal
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
