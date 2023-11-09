package com.dk24.moneycontrol.composables

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.entities.Pot
import com.dk24.moneycontrol.db.entities.PotTransactions
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@ExperimentalMaterial3Api
@Composable
fun AddUpdatePotDialogCompose(
    pot: Pot? = null,
    onDismissRequest: () -> Unit,
    onAdd: (Any) -> Unit
) {

    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Add Money", "Update Pot")

    var potNameValue by rememberSaveable { mutableStateOf(pot?.name.orEmpty()) }
    var totalAmountValue by rememberSaveable {
        mutableStateOf(
            if (pot?.totalAmount == null) "0" else pot.totalAmount.toString()
        )
    }
    var amountValue by rememberSaveable { mutableStateOf("") }

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

                if (pot == null) {
                    selectedIndex = 1
                }

                when (selectedIndex) {
                    0 -> {
                        AddMoney(
                            amount = amountValue,
                            amountChange = {
                                amountValue = it
                            })
                    }

                    1 -> {
                        AddUpdatePotViewCompose(
                            pot = pot,
                            name = potNameValue,
                            totalAmount = totalAmountValue,
                            nameChange = {
                                potNameValue = it
                            },
                            totalAmountChange = {
                                totalAmountValue = it
                            }
                        )
                    }
                }

                pot?.let {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SingleChoiceSegmentedButtonRow {
                            options.forEachIndexed { index, label ->
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = options.size
                                    ),
                                    onClick = { selectedIndex = index },
                                    selected = index == selectedIndex
                                ) {
                                    Text(label)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {
                            when (selectedIndex) {
                                0 -> {
                                    PotTransactions(
                                        amount = amountValue.toLong(),
                                        timestamp = System.currentTimeMillis()
                                    ).apply {
                                        this.potDetails.target = pot
                                        onAdd(this)
                                    }
                                }

                                1 -> {
                                    pot?.let {
                                        pot.apply {
                                            this.name = potNameValue
                                            this.totalAmount = totalAmountValue.toLong()
                                            onAdd(this)
                                        }
                                        return@TextButton
                                    }
                                    Pot(
                                        name = potNameValue,
                                        totalAmount = totalAmountValue.toLong(),
                                        savedAmount = 0L,
                                        isCompleted = false
                                    )

                                        .apply {
                                            onAdd(this)
                                        }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 12.dp),
                    ) {
                        Text(
                            if (pot != null) stringResource(id = R.string.update) else stringResource(
                                id = R.string.add
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddMoney(
    amount: String,
    amountChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(vertical = 12.dp),
        text = stringResource(id = R.string.add_amount),
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = amount, onValueChange = {
            amountChange(it)
        }, modifier = Modifier.padding(vertical = 12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
fun AddUpdatePotViewCompose(
    pot: Pot?,
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
            totalAmountChange(it)
        },
        modifier = Modifier.padding(vertical = 12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}
