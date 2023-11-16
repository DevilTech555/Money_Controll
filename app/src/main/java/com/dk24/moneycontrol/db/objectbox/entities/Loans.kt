package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Loans(
    @Id
    var id: Long = 0,
    var amount: Long? = null,
    var from: String? = null,
    var sourceType: String? = null,
    var numberOfInstalments: Int? = null,
    var numberOfInstalmentsPaid: Int? = null,
    var isAutoRepayEnabled: Boolean? = null,
    var autoRepayStartDate: String? = null
)
