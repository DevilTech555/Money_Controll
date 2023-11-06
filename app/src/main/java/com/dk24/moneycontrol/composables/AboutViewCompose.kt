package com.dk24.moneycontrol.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.viewmodels.AboutViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutViewCompose(drawerState: DrawerState) {

    val viewModel = viewModel<AboutViewModel>()

    Scaffold(
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        content = {  _ ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = "AboutViewCompose",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}
