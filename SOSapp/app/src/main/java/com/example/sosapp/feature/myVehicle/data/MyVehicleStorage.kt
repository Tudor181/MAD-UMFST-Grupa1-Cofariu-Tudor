package com.example.sosapp.feature.myVehicle.data

import android.content.Context
import com.google.gson.Gson

object MyVehicleStorage {
    private const val PREFS_NAME = "my_vehicle_prefs"
    private const val CONTACTS_KEY = "myVehicle"

    fun saveMyVehicle(context: Context, vehicle: MyVehicleModel) {
        val json = Gson().toJson(vehicle)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(CONTACTS_KEY, json).apply()
    }

    fun getVehicle(context: Context): MyVehicleModel? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(CONTACTS_KEY, null)
        return if (!json.isNullOrEmpty()) {
            Gson().fromJson(json, MyVehicleModel::class.java)
        } else {
            null
        }
    }
}
