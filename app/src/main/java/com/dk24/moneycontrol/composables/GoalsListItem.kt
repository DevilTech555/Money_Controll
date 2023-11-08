package com.dk24.moneycontrol.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dk24.moneycontrol.db.entities.MonthlyGoals
import com.dk24.moneycontrol.utilites.changeAlpha
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun GoalsListItem(
    monthlyGoals: MonthlyGoals,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: (MonthlyGoals) -> Unit,
    onEdit: (MonthlyGoals) -> Unit
) {

    val delete = SwipeAction(
        icon = { Icon(Icons.TwoTone.Delete, "") },
        background = Color.Transparent,
        onSwipe = {
            onDelete(monthlyGoals)
        }
    )

    val edit = SwipeAction(
        icon = { Icon(Icons.TwoTone.Edit, "") },
        background = Color.Transparent,
        onSwipe = {
            onEdit(monthlyGoals)
        }
    )

    SwipeableActionsBox(
        swipeThreshold = 70.dp,
        startActions = listOf(edit),
        endActions = listOf(delete)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 6.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.changeAlpha(.5f),
                    RoundedCornerShape(20)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = monthlyGoals.description.orEmpty(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            CircularCheckbox(
                checked = monthlyGoals.isAchieved ?: false, onCheckedChange = onCheckedChange
            )
        }
    }
}