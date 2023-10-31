package com.dk24.moneycontrol.db.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Account (
    @Id
    var id: Long = 0,
    var name: String? = null,
    var type: String? = null
)
