package com.dk24.moneycontrol.db.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Product(
    @Id
    var id: Long = 0,
    var name: String? = null
) {
    lateinit var category: ToOne<Category>
}
