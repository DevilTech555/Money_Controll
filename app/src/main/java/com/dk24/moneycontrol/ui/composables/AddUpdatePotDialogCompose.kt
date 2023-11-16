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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.room.model.MPot
import com.dk24.moneycontrol.db.room.model.MPotTransaction
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@ExperimentalMaterial3Api
@Composable
fun AddUpdatePotDialogCompose(
    mPot: MPot? = null,
    onDismissRequest: () -> Unit,
    onAdd: (Any?) -> Unit
) {

    var selectedIndex by remember { mutableIntStateOf(0) }

    val options = listOf("Add Money", "Update Pot")

    var potNameValue by rememberSaveable { mutableStateOf(mPot?.name.orEmpty()) }
    var totalAmountValue by rememberSaveable {
        mutableStateOf(
            if (mPot?.totalAmount == null) "0" else mPot.totalAmount.toString()
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

                if (mPot == null) {
                    selectedIndex = 1
                }

                when (selectedIndex) {
                    0 -> {
                        AddMoney(
                            mPot = mPot,
                            amount = amountValue,
                            amountChange = {
                                amountValue = it
                            })
                    }

                    1 -> {
                        AddUpdatePotViewCompose(
                            pot = mPot,
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

                mPot?.let {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

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
                                    if (amountValue.isBlank()) {
                                        onAdd(null)
                                        return@TextButton
                                    }
                                    mPot?.let {
                                        MPotTransaction(
                                            amount = amountValue.toFloat(),
                                            timestamp = System.currentTimeMillis(),
                                            moneyPotId = mPot.id
                                        ).apply {
                                            onAdd(this)
                                        }
                                    }
                                }

                                1 -> {
                                    mPot?.let {
                                        mPot.apply {
                                            this.name = potNameValue
                                            this.totalAmount = totalAmountValue.toFloat()
                                            onAdd(this)
                                        }
                                        return@TextButton
                                    }
                                    if (
                                        potNameValue.isBlank() && totalAmountValue.toLong() == 0L
                                    ) {
                                        onAdd(null)
                                        return@TextButton
                                    }
                                    MPot(
                                        name = potNameValue,
                                        totalAmount = totalAmountValue.toFloat(),
                                        savedAmount = 0F,
                                        isCompleted = false
                                    ).apply {
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
                            if (mPot != null) stringResource(id = R.string.update) else stringResource(
                                id = R.string.add
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddMoney(
    mPot: MPot?,
    amount: String,
    amountChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Text(
        modifier = Modifier.padding(vertical = 12.dp),
        text = stringResource(id = R.string.add_amount),
        style = MaterialTheme.typography.titleMedium
    )

    OutlinedTextField(
        value = amount, onValueChange = {
            if (it.isBlank()) {
                amountChange("")
                return@OutlinedTextField
            }
            if (it.isDigitsOnly() && it.toFloat() > 0) {
                mPot?.let { pot ->
                    if ((it.toFloat() + pot.savedAmount) <= pot.totalAmount)
                        amountChange(it)
                }
            } else amountChange("")
        }, modifier = Modifier
            .padding(vertical = 12.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboardController?.show()
                }
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

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
