package com.dk24.moneycontrol.ui.composables

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.ui.composables.customcomponents.TopBarCompose
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.utilites.getVersionNameAndVersionCode
import com.dk24.moneycontrol.viewmodels.AboutViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutViewCompose(drawerState: DrawerState, context: Context) {

    val viewModel = viewModel<AboutViewModel>()
    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bg),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Developed By:  Naveen P M")
                Text(text = "Application Version - Code:  ${getVersionNameAndVersionCode(context)}")
            }
        }
    )

    SetStatusBarColor(color = bg)
}
