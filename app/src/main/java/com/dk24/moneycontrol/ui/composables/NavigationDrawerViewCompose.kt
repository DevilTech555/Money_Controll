package com.dk24.moneycontrol.ui.composables

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.models.NavigationItem
import com.dk24.moneycontrol.utilites.getVersionNameAndVersionCode
import com.dk24.moneycontrol.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
fun UserDetailsNavigationDrawerItem() {

    val viewModel = viewModel<NavigationViewModel>()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_default_user),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .defaultMinSize(minHeight = 46.dp)
            ) {
                Text(text = viewModel.getUserName(), style = MaterialTheme.typography.titleLarge)
                Text(
                    text = viewModel.getSecondaryName(),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun ComposePreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        UserDetailsNavigationDrawerItem()
    }
}
