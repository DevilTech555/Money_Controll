package com.dk24.moneycontrol.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.viewmodels.PeggyBankViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeggyBankViewCompose(drawerState: DrawerState, context: Context) {
    val viewModel = viewModel<PeggyBankViewModel>()
    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(bg),
        floatingActionButton = {
            CircleFabButton(contentDescription = "") {

            }
        },
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        content = { _ ->

        }
    )
    
    SetStatusBarColor(color = bg)
}
