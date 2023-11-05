package com.dk24.moneycontrol.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

enum class TopBarNavigationType {

    MENU;

    @Composable
    fun GetIconButton(onClick: (TopBarNavigationType) -> Unit) {
        return when (this) {
            MENU -> {
                IconButton(onClick = { onClick(MENU) }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}
