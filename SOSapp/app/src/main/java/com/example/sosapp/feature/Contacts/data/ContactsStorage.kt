package com.example.sosapp.feature.Contacts.data

import android.content.Context
import com.google.gson.Gson

object ContactStorage {
    private const val PREFS_NAME = "contact_prefs"
    private const val CONTACTS_KEY = "contacts"

    fun saveContacts(context: Context, contacts: List<ContactModel>) {
        val json = Gson().toJson(contacts)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(CONTACTS_KEY, json).apply()
    }

    fun getContacts(context: Context): List<ContactModel> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(CONTACTS_KEY, null)
        return if (json != null) {
            Gson().fromJson(json, Array<ContactModel>::class.java).toList()
        } else {
            emptyList()
        }
    }
}
