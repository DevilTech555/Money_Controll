package com.dk24.moneycontrol.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.objectbox.entities.MonthlyGoals
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateGoalDialogCompose(
    monthlyGoal: MonthlyGoals?,
    onDismissRequest: () -> Unit,
    onUpdate: (MonthlyGoals?) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface.changeAlpha(Constants.dialogBackgroundAlpha)
    ) {
        Dialog(onDismissRequest = {
            onDismissRequest()
        }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.inversePrimary),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {

                var goalTextValue by rememberSaveable { mutableStateOf(monthlyGoal?.description.orEmpty()) }

                Text(
                    text = stringResource(id = R.string.update_goal),
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(value = goalTextValue, onValueChange = {
                    goalTextValue = it
                    monthlyGoal?.description = it
                }, modifier = Modifier.padding(vertical = 12.dp))

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { onUpdate(monthlyGoal) },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 12.dp),
                    ) {
                        Text(stringResource(id = R.string.update))
                    }
                }
            }
        }
    }
}
