package com.dk24.moneycontrol.db.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.dk24.moneycontrol.db.room.model.MPotTransaction

@Dao
interface MPotTransactionDao {
    @Insert
    fun insert(mPotTransaction: MPotTransaction)

    @Delete
    fun delete(mPotTransaction: MPotTransaction)
}
