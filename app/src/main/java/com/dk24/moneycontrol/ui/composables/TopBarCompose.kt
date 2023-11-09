package com.dk24.moneycontrol.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dk24.moneycontrol.enums.TopBarNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCompose(
    title: String?,
    drawerState: DrawerState,
    navigationType: TopBarNavigationType
) {
    TopAppBar(
        modifier = Modifier.padding(top = 16.dp),
        title = {
            Text(
                text = title.orEmpty(),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            TopBarNavigationType.valueOf(navigationType.name).GetIconButton(drawerState)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}
