package com.example.sosapp.feature.Contacts.data

import android.content.Context
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ContactsProvider constructor(
 private val context: Context
) {
    fun getContactsNames(): List<ContactModel> {
        val contactsList = mutableListOf<ContactModel>()
        context.contentResolver?.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use { contactsCursor ->
            val idIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
//            val numberIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.)

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                name?.let { contactsList.add(ContactModel(id, it)) }
            }
        }
        return contactsList
    }

    private fun getContactsNumbers(): Map<String, MutableList<String>> {
        val contactsNumbersMap = mutableMapOf<String, MutableList<String>>()
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use { phoneCursor ->
            val contactIdIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (phoneCursor.moveToNext()) {
                val contactId = phoneCursor.getString(contactIdIndex)
                val number = phoneCursor.getString(numberIndex)
                contactsNumbersMap.getOrPut(contactId) { mutableListOf() }.add(number)
            }
        }
        return contactsNumbersMap
    }
    suspend fun getContacts(): List<ContactModel> = coroutineScope {
        val names = async(Dispatchers.IO) { getContactsNames() }.await()
        val numbers = async(Dispatchers.IO) { getContactsNumbers() }.await()
//        val emails = async(Dispatchers.IO) { getContactsEmails() }.await()

        names.map { contact ->
            val contactNumbers = numbers[contact.id].orEmpty()
//            val contactEmails = emails[contact.id].orEmpty()
            ContactModel(contact.id, contact.name, contactNumbers)
        }
    }
}