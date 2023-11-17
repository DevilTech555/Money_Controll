package com.dk24.moneycontrol.ui.composables.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.models.NavigationItem
import com.dk24.moneycontrol.utilites.getVersionNameAndVersionCode
import com.dk24.moneycontrol.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationDrawerViewCompose(
    context: Context,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onClickOption: (NavigationItem) -> Unit,
    content: @Composable () -> Unit
) {

    val viewModel = viewModel<NavigationViewModel>()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                UserDetailsNavigationDrawerItem()
                Spacer(modifier = Modifier.height(16.dp))
                viewModel.getNavigationItems().forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == viewModel.selectedItemIndex.intValue,
                        onClick = {
                            viewModel.selectedItemIndex.intValue = index
                            scope.launch {
                                drawerState.close()
                            }
                            onClickOption(viewModel.getNavigationItem(index))
                        },
                        icon = {
                            (if (index == viewModel.selectedItemIndex.intValue) item.selectedIcon else item.unselectedIcon)?.let { icon ->
                                Icon(imageVector = icon, contentDescription = item.title)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = "version ${getVersionNameAndVersionCode(context, true)}",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.End),
                )
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}
