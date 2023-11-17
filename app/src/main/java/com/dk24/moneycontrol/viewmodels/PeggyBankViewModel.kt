package com.dk24.moneycontrol.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk24.moneycontrol.db.database.MoneyControlDatabase
import com.dk24.moneycontrol.db.model.MPot
import com.dk24.moneycontrol.db.model.MPotTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PeggyBankViewModel : ViewModel() {

    private var database: MoneyControlDatabase = MoneyControlDatabase.getInstance()

    var moneyPotList: Flow<List<MPot>> = database.mPotDao().getAllPotAsFlow()

    var selectedPot: MPot? = null

    fun addPot(mPot: MPot) {
        viewModelScope.launch(Dispatchers.IO) {
            database.mPotDao().insert(mPot)
        }
    }

    fun removePot(mPot: MPot) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTransactions(mPot)
            database.mPotDao().delete(mPot)
        }
    }


    fun updatePot(mPot: MPot) {
        viewModelScope.launch(Dispatchers.IO) {
            database.mPotDao().delete(mPot)
            launch {
                database.mPotDao().insert(mPot)
            }
        }
    }

    fun addMoneyToPot(mPotTransaction: MPotTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            database.mPotTransactionDao().insert(mPotTransaction)
            updateMoney()
        }
    }


    private fun updateMoney() {
        database.mPotDao().getAllPot().filter { !it.isCompleted }.forEach { mPot ->
            database.mPotDao().getAllTransactionForPot(mPot.id).apply {
                var savedTotal = 0f
                this.mPotTransaction.forEach {
                    savedTotal += it.amount
                }
                this.mPot.savedAmount = savedTotal
                if (this.mPot.totalAmount == savedTotal) {
                    this.mPot.isCompleted = true
                }
                updatePot(this.mPot)
            }
        }
    }

    private fun deleteAllTransactions(mPot: MPot) {
        database.mPotDao().getAllTransactionForPot(mPot.id).apply {
            this.mPotTransaction.forEach {
                database.mPotTransactionDao().delete(it)
            }
        }
    }
}
