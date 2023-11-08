package com.dk24.moneycontrol.viewmodels

import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.db.entities.Pot

class PeggyBankViewModel: ViewModel() {
    private val title = "Peggy Bank"

    private val potList = listOf(
        Pot(0,"Helmet", 5000L, 0L, false),
        Pot(0,"Tekken 8 Game", 5000L, 4000L, false),
        Pot(0,"Jacket", 10000L, 5000L, false)
    )

    fun getTitle() = title

    fun getPotList() = potList
}
