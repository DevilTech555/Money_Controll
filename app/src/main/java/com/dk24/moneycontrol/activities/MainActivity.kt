package com.dk24.moneycontrol.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dk24.moneycontrol.composables.NavigationDrawerViewCompose
import com.dk24.moneycontrol.navigation.MainViewNavigationHost
import com.dk24.moneycontrol.navigation.MainViewNavigationRoutes
import com.dk24.moneycontrol.ui.theme.MoneyControlTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyControlTheme {
                // A surface container using the 'background' color from  the theme
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationDrawerViewCompose(
                        context = this,
                        drawerState = drawerState,
                        { navItem ->
                            navItem.route?.let {
                                navController.navigate(it) {
                                    popUpTo(MainViewNavigationRoutes.MAIN_SCREEN)
                                }
                            }
                        }) {
                        MainViewNavigationHost(
                            navController = navController,
                            drawerState = drawerState,
                            context = this
                        )
                    }
                }
            }
        }
    }
}
