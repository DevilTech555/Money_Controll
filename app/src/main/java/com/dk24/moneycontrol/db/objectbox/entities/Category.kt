package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Category (
    @Id
    var id: Long = 0,
    var name: String? = null
)
