package com.dk24.moneycontrol.models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector?,
    val unselectedIcon: ImageVector?,
    val badgeCount: Int? = null,
    val route: String? = null
)
