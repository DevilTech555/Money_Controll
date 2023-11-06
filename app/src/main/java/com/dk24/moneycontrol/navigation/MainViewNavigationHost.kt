package com.dk24.moneycontrol.navigation

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dk24.moneycontrol.composables.AboutViewCompose
import com.dk24.moneycontrol.composables.DashboardViewCompose
import com.dk24.moneycontrol.composables.MonthlyGoalsViewCompose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainViewNavigationHost(
    navController: NavHostController,
    drawerState: DrawerState,
    context: Context
) {

    NavHost(
        navController = navController,
        startDestination = MainViewNavigationRoutes.MAIN_SCREEN
    ) {
        composable(MainViewNavigationRoutes.MAIN_SCREEN) {
            DashboardViewCompose(drawerState = drawerState)
        }
        composable(MainViewNavigationRoutes.MONTHLY_GOALS_SCREEN) {
            MonthlyGoalsViewCompose(drawerState = drawerState)
        }
        composable(MainViewNavigationRoutes.ABOUT_SCREEN) {
            AboutViewCompose(drawerState = drawerState, context)
        }
    }
}