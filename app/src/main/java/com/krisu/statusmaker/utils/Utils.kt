package com.krisu.statusmaker.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {

    fun hasInternetConnection(context: Context?): Boolean {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun saveStringInSP(context: Context, key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putString(key, value)
        myEdit.commit()
    }

    fun getStringInSP(context: Context, key: String): String? {
        val sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        return sh.getString(key, "")
    }

    fun saveBooleanInSP(context: Context, key: String, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putBoolean(key, value)
        myEdit.commit()
    }

    fun getBooleanInSP(context: Context, key: String): Boolean {
        val sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        return sh.getBoolean(key, false)
    }

    fun saveIntInSP(context: Context, key: String, value: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putInt(key, value)
        myEdit.commit()
    }

    fun getIntInSP(context: Context, key: String): Int {
        val sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        return sh.getInt(key, -1)
    }

    fun getCustomFont(context: Context, fontName: String): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/$fontName")
    }
}