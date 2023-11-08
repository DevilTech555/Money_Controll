package com.dk24.moneycontrol.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    items(viewModel.getPotList()) { pot ->
                        PeggyBankPotViewCompose(pot = pot, onClick = {

                        })
                    }
                }
            }
        }
    )

    SetStatusBarColor(color = bg)
}
