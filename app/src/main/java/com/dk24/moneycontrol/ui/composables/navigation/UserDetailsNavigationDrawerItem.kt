package com.dk24.moneycontrol.ui.composables.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.viewmodels.NavigationViewModel

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
