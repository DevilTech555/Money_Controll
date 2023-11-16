package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class LentMoney(
    @Id
    var id: Long = 0,
    var amount: Long? = null,
    var name: String? = null,
    var timeStamp: String? = null,
    var mobileNumber: Long? = null,
    var isRepaid: Boolean? = null
)
