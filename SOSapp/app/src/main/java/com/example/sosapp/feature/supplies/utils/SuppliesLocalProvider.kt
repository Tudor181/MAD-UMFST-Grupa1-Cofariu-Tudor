package com.example.sosapp.feature.supplies.utils

import android.content.Context
import com.example.sosapp.feature.supplies.data.SupplyModel
import com.google.gson.Gson

object SuppliesLocalProvider {
    private const val PREFS_NAME = "supplies_prefs"
    private const val SUPPLIES_KEY = "supplies"

    fun saveSupplies(context: Context, contacts: List<SupplyModel>) {
        val json = Gson().toJson(contacts)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(SUPPLIES_KEY, json).apply()
    }

    fun getSupplies(context: Context): List<SupplyModel> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(SUPPLIES_KEY, null)
        return if (json != null) {
            Gson().fromJson(json, Array<SupplyModel>::class.java).toList()
        } else {
            emptyList()
        }
    }
}
