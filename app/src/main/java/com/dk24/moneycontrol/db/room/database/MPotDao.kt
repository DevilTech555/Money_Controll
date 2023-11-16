package com.dk24.moneycontrol.db.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dk24.moneycontrol.db.room.model.MPot
import com.dk24.moneycontrol.db.room.model.MPotAndMPotTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface MPotDao {

    @Insert
    suspend fun insert(mPot: MPot)

    @Delete
    suspend fun delete(mPot: MPot)

    @Update
    suspend fun update(mPot: MPot)

    @Transaction
    @Query("SELECT * FROM Money_Pot WHERE id = :potId")
    fun getAllTransactionForPot(potId: Long): MPotAndMPotTransactions

    @Query("SELECT * FROM Money_Pot WHERE id = :id")
    fun getPotById(id: Long): Flow<MPot>

    @Query("SELECT * FROM Money_Pot")
    fun getAllPotAsFlow(): Flow<List<MPot>>

    @Query("SELECT * FROM Money_Pot")
    fun getAllPot(): List<MPot>
}
