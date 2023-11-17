package com.dk24.moneycontrol.db.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dk24.moneycontrol.db.model.MPot
import com.dk24.moneycontrol.db.model.MPotTransaction
import com.dk24.moneycontrol.db.model.MonthlyGoal

@Database(
    entities = [MonthlyGoal::class, MPot::class, MPotTransaction::class],
    version = 1
)
abstract class MoneyControlDatabase : RoomDatabase() {

    abstract fun monthlyGoalDao(): MonthlyGoalDao
    abstract fun mPotDao(): MPotDao
    abstract fun mPotTransactionDao(): MPotTransactionDao


    companion object {

        private var instance: MoneyControlDatabase? = null

        @Synchronized
        fun init(application: Application) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application.applicationContext, MoneyControlDatabase::class.java,
                    "money_control_database"
                )
                    .build()
            }
        }

        fun getInstance(): MoneyControlDatabase {
            return instance
                ?: throw IllegalStateException("Database not initialized. Call init() before accessing the database.")
        }
    }

}
