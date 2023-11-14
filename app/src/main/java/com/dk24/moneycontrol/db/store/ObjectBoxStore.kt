package com.dk24.moneycontrol.db.store

import android.content.Context
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.db.entities.MyObjectBox
import com.dk24.moneycontrol.db.entities.Pot
import com.dk24.moneycontrol.db.entities.PotTransactions
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.flow
import kotlinx.coroutines.ExperimentalCoroutinesApi

object ObjectBoxStore {

    private lateinit var store: BoxStore

    private var monthlyGoalsBox: Box<MonthlyGoals>? = null
    private var potsBox: Box<Pot>? = null
    private var potTransactionsBox: Box<PotTransactions>? = null


    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .name("moneyControl")
            .build()
    }

    fun get(): BoxStore = store

    private fun getBoxForMonthlyGoals(): Box<MonthlyGoals> = monthlyGoalsBox ?: synchronized(this) {
        monthlyGoalsBox ?: store.boxFor(MonthlyGoals::class.java).also {
            monthlyGoalsBox = it
        }
    }

    fun getBoxForPots(): Box<Pot> = potsBox ?: synchronized(this) {
        potsBox ?: store.boxFor(Pot::class.java).also {
            potsBox = it
        }
    }

    fun getBoxForPotTransactions(): Box<PotTransactions> =
        potTransactionsBox ?: synchronized(this) {
            potTransactionsBox ?: store.boxFor(PotTransactions::class.java).also {
                potTransactionsBox = it
            }
        }

    fun insertMonthlyGoal(goal: String): Boolean {
        MonthlyGoals(
            description = goal,
            date = System.currentTimeMillis(),
            isAchieved = false
        ).also {
            return try {
                getBoxForMonthlyGoals().put(it)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    fun removeMonthlyGoal(monthlyGoals: MonthlyGoals?): Boolean {
        if (monthlyGoals == null)
            return false
        return try {
            getBoxForMonthlyGoals().remove(monthlyGoals)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun updateMonthlyGoal(monthlyGoals: MonthlyGoals?): Boolean {
        if (monthlyGoals == null)
            return false
        return try {
            getBoxForMonthlyGoals().remove(monthlyGoals)
            getBoxForMonthlyGoals().put(monthlyGoals)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllMonthlyGoalsAsFlow() =
        getBoxForMonthlyGoals().query().build().flow()
}
