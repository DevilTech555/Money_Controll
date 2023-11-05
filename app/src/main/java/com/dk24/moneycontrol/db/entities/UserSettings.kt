package com.dk24.moneycontrol.db.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class UserSettings(
    @Id
    var id: Long = 0,
    var property: String? = null,
    var type: String? = null,
    var value: String? = null
)
