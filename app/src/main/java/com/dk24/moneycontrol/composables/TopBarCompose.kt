package com.dk24.moneycontrol.composables

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.dk24.moneycontrol.enums.TopBarNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCompose(
    title: String?,
    drawerState: DrawerState,
    navigationType: TopBarNavigationType
) {
    TopAppBar(
        title = {
            Text(text = title.orEmpty())
        },
        navigationIcon = {
            TopBarNavigationType.valueOf(navigationType.name).GetIconButton(drawerState)
        }
    )
}
