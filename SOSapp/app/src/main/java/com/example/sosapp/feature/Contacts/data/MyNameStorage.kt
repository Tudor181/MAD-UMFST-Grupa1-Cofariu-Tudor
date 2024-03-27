package com.example.sosapp.feature.Contacts.data

import android.content.Context
import com.example.sosapp.feature.myVehicle.data.MyVehicleModel
import com.google.gson.Gson

object MyNameStorage {
    private const val PREFS_NAME = "my_name_prefs"
    private const val CONTACTS_KEY = "myName"

    fun saveName(context: Context, name:String) {
        val json = Gson().toJson(name)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(CONTACTS_KEY, json).apply()
    }

    fun getName(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(CONTACTS_KEY, null)
        return if (!json.isNullOrEmpty()) {
            Gson().fromJson(json, String()::class.java)
        } else {
            null
        }
    }
}


