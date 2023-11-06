package com.dk24.moneycontrol.utilites

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.pm.PackageInfoCompat.getLongVersionCode

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@Suppress("DEPRECATION")
fun getVersionNameAndVersionCode(context: Context, onlyVersionName: Boolean = false): String {
    var pInfo: PackageInfo? = null
    try {
        pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        Log.wtf("CRASH", e.message)
    }
    val version = pInfo?.versionName
    val versionCode = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) pInfo?.let {
        getLongVersionCode(
            it
        )
    } else pInfo?.versionCode

    return "$version" + if (onlyVersionName) "" else " - $versionCode"
}
