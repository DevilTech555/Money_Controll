package com.dk24.moneycontrol.utilites

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

object DateTimeFormatterHelper {
    @Composable
    fun DatePickerDialog(callBack: (String) -> Unit) {
        Calendar.getInstance().apply {
            DatePickerDialog(
                LocalContext.current,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    callBack("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
                },
                this[Calendar.YEAR],
                this[Calendar.MONTH],
                this[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

}
