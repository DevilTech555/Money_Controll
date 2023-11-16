package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Savings(
    @Id
    var id: Long = 0,
    var totalAmount: Long? = null
) {
    lateinit var account: ToOne<Account>
}
