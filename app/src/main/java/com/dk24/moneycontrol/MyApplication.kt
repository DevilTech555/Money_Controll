package com.dk24.moneycontrol

import android.app.Application
import com.dk24.moneycontrol.db.room.database.MoneyControlDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MoneyControlDatabase.init(this)
    }
}
