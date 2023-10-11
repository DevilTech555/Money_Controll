package com.dk24.moneycontrol.utilites

import android.content.Context
import android.content.SharedPreferences

class SharedPreferHelper {

    companion object {
        @Volatile private var INSTANCE: SharedPreferHelper? = null

        private var sharedPreferences: SharedPreferences? = null
        private var sharedPreferencesEditor: SharedPreferences.Editor? = null

        fun getInstance(context: Context): SharedPreferHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildSharedPreferences().also {
                    INSTANCE = it
                    initSharedPrefs(context)
                }
            }

        private fun initSharedPrefs(context: Context) {
            sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            sharedPreferencesEditor = sharedPreferences?.edit()
        }

        private fun buildSharedPreferences() = SharedPreferHelper()


        fun put(key: Key, value: String) {
            doEdit()
            sharedPreferencesEditor?.putString(key.name, value)
            doCommit()
        }

        fun getString(key: Key): String? = sharedPreferences?.getString(key.name, "")

        private fun doEdit() {
            if (sharedPreferencesEditor == null) {
                sharedPreferencesEditor = sharedPreferences?.edit()
            }
        }

        private fun doCommit() {
            if (sharedPreferencesEditor != null) {
                sharedPreferencesEditor?.apply()
                sharedPreferencesEditor = null
            }
        }


        fun clear() {
            doEdit()
            sharedPreferencesEditor?.clear()
            doCommit()
        }

        enum class Key {
            GROOMING_START_DATE,
            GROOMING_END_DATE,
            DEVELOPMENT_START_DATE,
            DEVELOPMENT_END_DATE,
            RELEASE_START_DATE,
            RELEASE_END_DATE
        }
    }
}
