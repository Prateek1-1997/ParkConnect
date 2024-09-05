package com.vehicle.owner.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.vehicle.owner.data.local.ISharedPreference.Companion.IS_VERIFIED
import com.vehicle.owner.data.local.ISharedPreference.Companion.SHARED_PREF
import com.vehicle.owner.data.local.ISharedPreference.Companion.TOKEN
import com.vehicle.owner.data.local.ISharedPreference.Companion.USER_ID

class SharedPreferences(
    private val sharedPref: SharedPreferences,
    context: Context,
    private var gson: Gson,
) : ISharedPreference {

    init {
        initSharedPref(context = context)
        gson = Gson()
    }

    private lateinit var splashSharedPref: SharedPreferences
    private lateinit var splashSharedPrefEditor: SharedPreferences.Editor
    private fun initSharedPref(
        context: Context,
    ) {
        splashSharedPref = context.getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE,
        )
        splashSharedPrefEditor = splashSharedPref.edit()
    }

    override fun getAuthToken(): String {
        return sharedPref.getString(
            TOKEN,
            "",
        ).orEmpty()
    }

    override fun saveAuthToken(authToken: String) {
        sharedPref.edit().putString(
            TOKEN,
            authToken,
        ).apply()
    }

    override fun getUserId(): String {
        return sharedPref.getString(
            USER_ID,
            "",
        ).orEmpty()
    }

    override fun saveUserId(userId: String) {
        sharedPref.edit().putString(
            USER_ID,
            userId,
        ).apply()
    }

    override fun isVerified(): Boolean {
        return sharedPref.getBoolean(
            IS_VERIFIED,
            false,
        )
    }

    override fun saveVerified(isVerified: Boolean) {
        sharedPref.edit().putBoolean(
            IS_VERIFIED,
            isVerified,
        ).apply()
    }


}
