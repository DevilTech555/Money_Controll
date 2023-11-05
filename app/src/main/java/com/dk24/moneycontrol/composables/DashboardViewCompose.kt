package com.dk24.moneycontrol.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dk24.moneycontrol.enums.TopBarNavigationType

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardViewCompose(title: String, onClick: (TopBarNavigationType) -> Unit) {
    Scaffold(
        topBar = {
            TopBarCompose(
                title = title,
                onClick = onClick,
                navigationType = TopBarNavigationType.MENU
            )
        },
        content = { _ ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = "DashboardViewCompose",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}
