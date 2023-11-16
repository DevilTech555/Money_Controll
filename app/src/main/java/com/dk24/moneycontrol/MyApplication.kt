package com.dk24.moneycontrol

import android.app.Application
import com.dk24.moneycontrol.db.objectbox.store.ObjectBoxStore

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBoxStore.init(this)
    }
}
