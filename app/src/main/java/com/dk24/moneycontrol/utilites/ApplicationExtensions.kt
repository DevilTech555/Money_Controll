package com.dk24.moneycontrol.utilites

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.content.pm.PackageInfoCompat.getLongVersionCode
import com.google.accompanist.systemuicontroller.rememberSystemUiController

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Color.changeAlpha(alphaValue: Float): Color = Color(
    red = this.red,
    green = this.green,
    blue = this.blue,
    alpha = alphaValue
)

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

@Composable
fun SetStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }
}
