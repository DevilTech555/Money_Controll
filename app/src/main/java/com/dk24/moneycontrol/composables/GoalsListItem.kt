package com.dk24.moneycontrol.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.utilites.changeAlpha

@Composable
fun GoalsListItem(monthlyGoals: MonthlyGoals, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer.changeAlpha(.5f), RoundedCornerShape(20))
            .padding(16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = monthlyGoals.description.orEmpty(),
            modifier = Modifier.padding(end = 12.dp).weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        CircularCheckbox(
            checked = monthlyGoals.isAchieved ?: false, onCheckedChange = onCheckedChange
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun Preview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        GoalsListItem(monthlyGoals = MonthlyGoals(1, "TEXT", 0L, true),
            onCheckedChange = {})
    }
}
