package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class MonthlyGoals(
    @Id
    var id: Long = 0,
    var description: String? = null,
    var date: Long? = null,
    var isAchieved: Boolean
)
