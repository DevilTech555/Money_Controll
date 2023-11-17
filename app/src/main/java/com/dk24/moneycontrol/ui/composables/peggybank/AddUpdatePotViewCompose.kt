package com.dk24.moneycontrol.ui.composables.peggybank

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.model.MPot

@Composable
fun AddUpdatePotViewCompose(
    pot: MPot?,
    name: String,
    totalAmount: String,
    nameChange: (String) -> Unit,
    totalAmountChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(vertical = 12.dp),
        text = if (pot != null) stringResource(id = R.string.update_pot) else stringResource(
            id = R.string.add_pot
        ),
        style = MaterialTheme.typography.titleMedium
    )

    Text(
        text = stringResource(id = R.string.pot_name),
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(value = name, onValueChange = {
        nameChange(it)
    }, modifier = Modifier.padding(vertical = 12.dp))

    Text(
        text = stringResource(id = R.string.total_amount),
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = totalAmount,
        onValueChange = {
            if (it.isBlank()) {
                totalAmountChange("")
                return@OutlinedTextField
            }
            if (it.isDigitsOnly() && it.toLong() > 0) totalAmountChange(it) else totalAmountChange("")
        },
        modifier = Modifier.padding(vertical = 12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}
