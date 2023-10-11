package com.dk24.moneycontrol.utilites

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,message, duration).show()
}
