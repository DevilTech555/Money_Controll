package com.dk24.moneycontrol.ui.composables.peggybank

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
import com.dk24.moneycontrol.db.model.MPot
import com.dk24.moneycontrol.db.model.MPotTransaction
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@ExperimentalMaterial3Api
@Composable
fun AddUpdatePotDialogCompose(
    viewType: ViewType,
    mPot: MPot? = null,
    onDismissRequest: () -> Unit,
    onAdd: (Any?) -> Unit
) {

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

                when (viewType) {
                    ViewType.POTT -> {
                        AddMoneyToPotComposeDialog(
                            mPot = mPot,
                            amount = amountValue,
                            amountChange = {
                                amountValue = it
                            })
                    }

                    ViewType.POT -> {
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

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {
                            when (viewType) {
                                ViewType.POTT -> {
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

                                ViewType.POT -> {
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
