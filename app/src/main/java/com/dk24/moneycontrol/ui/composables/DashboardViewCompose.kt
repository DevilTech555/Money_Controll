package com.dk24.moneycontrol.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.ui.composables.customcomponents.TopBarCompose
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.viewmodels.DashboardViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardViewCompose(drawerState: DrawerState) {
    val viewModel = viewModel<DashboardViewModel>()
    val bg = MaterialTheme.colorScheme.background
    Scaffold(
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        content = { _ ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bg)
            ) {

            }
        }
    )
    SetStatusBarColor(color = bg)
}
