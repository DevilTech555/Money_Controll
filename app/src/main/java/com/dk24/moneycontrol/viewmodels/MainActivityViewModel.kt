package com.dk24.moneycontrol.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.models.NavigationItem

class MainActivityViewModel: ViewModel() {

    private val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "About",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        )
    )
    private val title = "Hi, Naveen..!"

    fun getNavigationItems() = navigationItems

    fun getTitle() = title
}
