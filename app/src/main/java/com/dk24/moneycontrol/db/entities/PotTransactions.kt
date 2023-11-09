package com.dk24.moneycontrol.db.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class PotTransactions(
    @Id
    var id: Long = 0,
    var amount: Long? = null,
    var timestamp: Long? = null
) {
    lateinit var potDetails: ToOne<Pot>
}
