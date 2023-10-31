package com.dk24.moneycontrol.db.store

import android.content.Context
import com.dk24.moneycontrol.db.entities.MyObjectBox
import io.objectbox.BoxStore

object ObjectBoxStore {

    private const val TAG = "ObjectBoxStore"
    private lateinit var store: BoxStore

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .name("moneyControl")
            .build()
    }

    fun get(): BoxStore = store
}
