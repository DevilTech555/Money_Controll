package com.dk24.moneycontrol.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Monthly_Goal")
data class MonthlyGoal(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var description: String,
    var date: Long,
    var isAchieved: Boolean
)
