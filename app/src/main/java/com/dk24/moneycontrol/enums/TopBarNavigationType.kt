package com.dk24.moneycontrol.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

enum class TopBarNavigationType {

    MENU;

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GetIconButton(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
        return when (this) {
            MENU -> {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}
