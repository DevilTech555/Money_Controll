package com.dk24.moneycontrol.db.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DayDreams(
    @Id
    var id: Long = 0,
    var description: String? = null,
    var timeStamp: Long? = null,
    var status: String? = null
)
