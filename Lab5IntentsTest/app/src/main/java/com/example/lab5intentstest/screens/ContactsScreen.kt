package com.example.lab5intentstest.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab5intentstest.ContactsProvider
import com.example.lab5intentstest.R
import com.example.lab5intentstest.enums.DContact
import kotlin.coroutines.suspendCoroutine

@Composable
fun ContactsScreen(contactsProvider: ContactsProvider){
    val contactsList = remember { mutableStateListOf<DContact>() }

    LaunchedEffect(true){
        val fetchedContacts = contactsProvider.getContacts()
        contactsList.clear()
        contactsList.addAll(fetchedContacts)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(contactsList) { contact ->
            ContactItem(contact = contact)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun ContactItem(contact: DContact) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle contact click */ }
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.home),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = MaterialTheme.shapes.small)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = contact.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "id ${contact.id}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(

                text = contact.numbers[0].orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.clickable{ dial(context,contact.numbers[0].orEmpty()) }

            )
        }
    }
}

fun dial(ctx:Context, number:String?){
     Intent(Intent.ACTION_DIAL ).also{
        it.data = Uri.parse("tel:$number")
        try {
            ctx.startActivity(it, null)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}