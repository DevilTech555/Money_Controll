package com.dk24.moneycontrol.ui.composables.peggybank

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.model.MPot

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddMoneyToPotComposeDialog(
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
