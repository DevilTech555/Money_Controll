package com.dk24.moneycontrol.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.models.NavigationItem

class MainActivityViewModel : ViewModel() {

    private val navigationItems = listOf(
        NavigationItem(
            title = "Dashboard",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "Goals",
            selectedIcon = Icons.Filled.ThumbUp,
            unselectedIcon = Icons.Outlined.ThumbUp,
        ),
        NavigationItem(
            title = "About",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        )
    )

    private val title = "Hi, Naveen..!"
    private val userName = "Naveen PM"
    private val secondaryName = "pmnaveeen6@gmail.com"

    fun getNavigationItems() = navigationItems

    fun getUserName() = userName

    fun getSecondaryName() = secondaryName

    fun getTitle() = title
}
