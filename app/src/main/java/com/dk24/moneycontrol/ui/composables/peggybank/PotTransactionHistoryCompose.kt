package com.dk24.moneycontrol.ui.composables.peggybank

import androidx.compose.foundation.background
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.ui.composables.customcomponents.TopBarCompose
import com.dk24.moneycontrol.utilites.Constants


@Composable
fun PotTransactionHistoryCompose(drawerState: DrawerState) {
    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(bg),
        topBar = {
            TopBarCompose(
                title = Constants.POT_VIEW_TITLE,
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        }
    ) { innerPadding ->

    }
}
