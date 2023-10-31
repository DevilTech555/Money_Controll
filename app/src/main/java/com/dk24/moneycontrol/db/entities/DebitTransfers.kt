package com.dk24.moneycontrol.db.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class DebitTransfers(
    @Id
    var id: Long = 0,
    var amount: Long? = null,
    var timeStamp: String? = null
) {
    lateinit var product: ToOne<Product>
    lateinit var from: ToOne<Account>
}
