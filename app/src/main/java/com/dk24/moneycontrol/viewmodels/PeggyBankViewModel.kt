package com.dk24.moneycontrol.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.db.entities.Pot
import com.dk24.moneycontrol.db.entities.PotTransactions
import com.dk24.moneycontrol.db.store.ObjectBoxStore

class PeggyBankViewModel : ViewModel() {

    private val peggyBankBox = ObjectBoxStore.get().boxFor(Pot::class.java)
    private val peggyBankTransactionsBox = ObjectBoxStore.get().boxFor(PotTransactions::class.java)

    private val title = "Peggy Bank"

    private val potList: MutableList<Pot> = mutableListOf()

    val isUpdatePotDialogVisible = mutableStateOf(false)
    val isAddPotDialogVisible = mutableStateOf(false)
    val isDeletePotDialogVisible = mutableStateOf(false)

    var selectedPot: Pot? = null

    fun getPotList(): MutableList<Pot> {
        updateTotalSavings()
        resetPotList()
        return potList
    }

    private fun resetPotList() {
        potList.clear()
        potList.addAll(peggyBankBox.all)
    }

    fun addPot(pot: Pot) {
        peggyBankBox.put(pot)
        resetPotList()
        isAddPotDialogVisible.value = false
    }

    fun updatePot(pot: Pot) {
        peggyBankBox.put(pot)
        resetPotList()
        isUpdatePotDialogVisible.value = false
    }

    fun addMoney(potTransaction: PotTransactions) {
        peggyBankTransactionsBox.put(potTransaction)
        updateTotalSavings()
        resetPotList()
        isUpdatePotDialogVisible.value = false
    }

    fun removePot(pot: Pot) {
        deleteTransactions(pot)
        peggyBankBox.remove(pot)
        resetPotList()
        isDeletePotDialogVisible.value = false
    }


    private fun updateTotalSavings() {
        val pots = peggyBankBox.all.filter { it.isCompleted == false }
        val potTransactions = peggyBankTransactionsBox.all.groupBy { it.potDetails.target.id }

        pots.forEach { pot ->
            potTransactions[pot.id]?.let { transactions ->
                var savedTotal = 0L
                transactions.forEach {
                    it.amount?.let { amount ->
                        savedTotal += amount
                    }
                }

                if (savedTotal != 0L) {
                    pot.savedAmount = savedTotal
                    peggyBankBox.put(pot)
                }
            }
        }
    }

    private fun deleteTransactions(pot: Pot) {
        val potTransactions = peggyBankTransactionsBox.all.groupBy { it.potDetails.target.id }
        if (potTransactions.containsKey(pot.id)) {
            potTransactions[pot.id]?.let { transitions ->
                transitions.forEach {
                    peggyBankTransactionsBox.remove(it)
                }
            }
        }
    }

    fun getTitle() = title

}
