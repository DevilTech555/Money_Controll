package com.dk24.moneycontrol.db.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class CreditTransfers(
    @Id
    var id: Long = 0,
    var amount: Long? = null,
    var from: String? = null,
    var type: String? = null
) {
    lateinit var to: ToOne<Account>
}

