package com.example.sosapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.sosapp.feature.Contacts.data.ContactsProvider
import com.example.sosapp.feature.Contacts.ui.ContactsPermission
import com.example.sosapp.ui.theme.SOSappTheme

class PhoneBookActivity : ComponentActivity() {

    private val contactsProvider by lazy {
        ContactsProvider(context = this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SOSappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactsPermission(contactsProvider)
                }
            }
        }
    }
}





