package com.dk24.moneycontrol.db.store

import android.content.Context
import com.dk24.moneycontrol.db.entities.MyObjectBox
import io.objectbox.BoxStore

object ObjectBoxStore {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}
