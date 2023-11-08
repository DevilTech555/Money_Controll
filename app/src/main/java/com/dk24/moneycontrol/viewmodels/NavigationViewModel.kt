package com.dk24.moneycontrol.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.dk24.moneycontrol.models.NavigationItem
import com.dk24.moneycontrol.navigation.MainViewNavigationRoutes

class NavigationViewModel : ViewModel() {

    private val navigationItems = listOf(
        NavigationItem(
            title = "Dashboard",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = MainViewNavigationRoutes.MAIN_SCREEN
        ),
        NavigationItem(
            title = "Goals",
            selectedIcon = Icons.Filled.ThumbUp,
            unselectedIcon = Icons.Outlined.ThumbUp,
            route = MainViewNavigationRoutes.MONTHLY_GOALS_SCREEN
        ),
        NavigationItem(
            title = "Peggy Bank",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            route = MainViewNavigationRoutes.POT_SCREEN
        ),
        NavigationItem(
            title = "About",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = MainViewNavigationRoutes.ABOUT_SCREEN
        )
    )

    private val userName = "Naveen PM"
    private val secondaryName = "pmnaveeen6@gmail.com"

    val selectedItemIndex = mutableIntStateOf(0)

    fun getNavigationItems() = navigationItems

    fun getNavigationItem(index: Int) = navigationItems[index]

    fun getUserName() = userName

    fun getSecondaryName() = secondaryName
}
