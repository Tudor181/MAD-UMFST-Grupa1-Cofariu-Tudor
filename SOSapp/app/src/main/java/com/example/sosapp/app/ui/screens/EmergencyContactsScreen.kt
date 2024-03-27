package com.example.sosapp.app.ui.screens

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sosapp.R
import com.example.sosapp.app.MainActivity
import com.example.sosapp.app.PhoneBookActivity
import com.example.sosapp.feature.Contacts.data.ContactModel
import com.example.sosapp.feature.Contacts.data.ContactStorage
import com.example.sosapp.feature.Contacts.data.ContactsProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactsScreen(navController: NavController){
    val context = LocalContext.current
    val contactStorage = ContactStorage
    val contactsList = remember { mutableStateListOf<ContactModel>() }
    var activityResumed by remember { mutableStateOf(false) }



    val contactsProvider by lazy {
        ContactsProvider(context)
    }

    LaunchedEffect(true){
//        val fetchedContacts = contactsProvider.getContacts()
//        contactsList.clear()
        contactsList.addAll(contactStorage.getContacts(context))
    }

    LaunchedEffect(activityResumed) {
        if (activityResumed) {
            contactsList.clear()
            contactsList.addAll(contactStorage.getContacts(context))
        }
    }

    DisposableEffect(Unit) {
        val callback = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityResumed(activity: Activity) {
                if (activity is MainActivity) {
                    activityResumed = true
                }
            }

            override fun onActivityPaused(activity: Activity) {
                if (activity is MainActivity) {
                    activityResumed = false
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        }

        val appContext = context.applicationContext as Application
        appContext.registerActivityLifecycleCallbacks(callback)

        onDispose {
            appContext.unregisterActivityLifecycleCallbacks(callback)
        }
    }

    fun startPhoneBook(){
        val intentToPhoneBookActivity = Intent(context, PhoneBookActivity::class.java)
        context.startActivity(intentToPhoneBookActivity)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Emergency Contacts",
                            style = MaterialTheme.typography.headlineSmall,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W900,
                            modifier = Modifier.padding(start = 16.dp)
                        )
//                    }
                        },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) {paddingValues->
        if(contactsList.isNullOrEmpty()){
            HandleEmptyList()
            return@Scaffold
        }
        Column(modifier= Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()) {
                LazyColumn()
                {
                    items(contactsList) { contact ->
                        ContactItem(contact = contact)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            startPhoneBook()
                        }
                    ) {
                        Text("Edit list")
                    }
                }
            }
    }

}


@Composable
fun HandleEmptyList() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You have no contacts selected.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please select one, open contacts, and select the one you want.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val intentToPhoneBookActivity = Intent(context, PhoneBookActivity::class.java)
                context.startActivity(intentToPhoneBookActivity)
            }
        ) {
            Text("Open Contacts")
        }
    }
}
