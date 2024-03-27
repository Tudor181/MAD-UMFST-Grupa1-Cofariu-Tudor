package com.example.sosapp.app.ui.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sosapp.R
import com.example.sosapp.feature.Contacts.data.ContactModel
import com.example.sosapp.feature.Contacts.data.ContactStorage
import com.example.sosapp.feature.Contacts.data.ContactsProvider
import com.example.sosapp.feature.Contacts.utils.ContactAlertDialog


@Composable
fun PhoneContactsScreen(contactsProvider: ContactsProvider){
    val context = LocalContext.current
    val contactStore = ContactStorage
    var showDialog by remember { mutableStateOf(false) }
    var contactsList = remember { mutableStateListOf<ContactModel>() }
    val selectedContacts = remember { mutableStateListOf<ContactModel>() }
    val prevSelectedContacts = remember { mutableStateListOf<ContactModel>() }

    LaunchedEffect(true){
        Log.d("contactsFetch", "LAUNCH, get contacts")
        val fetchedContacts = contactsProvider.getContacts().toMutableList()
        val selectedContactsFromStore = contactStore.getContacts(context)

        selectedContactsFromStore.forEach{ selectedContact->

            fetchedContacts.removeAll{ it.id == selectedContact.id }
            val updatedContact = selectedContact.copy(isSelected = true)
            fetchedContacts.add(updatedContact)
        }
        fetchedContacts.sortBy { it.id }
        selectedContacts.addAll(selectedContactsFromStore)
        prevSelectedContacts.addAll(selectedContactsFromStore)
        contactsList.clear()
        contactsList.addAll(fetchedContacts)
        Log.d("contactsFetch", "LAUNCH, get contacts2 $fetchedContacts ")
        
    }

    fun saveNewStateInStore(){
        selectedContacts.sortBy { it.id }
        contactStore.saveContacts(context, selectedContacts)
        (context as Activity).finish()
    }



    fun onSelectContact(contact: ContactModel){
        Log.d("contactsFetch", "selected $contact")
        Log.d("contactsFetch", "list ${selectedContacts.toList()}")

        if(selectedContacts.find { it.id === contact.id  } == null) {
            Log.d("contactsFetch", "to be added $contact")
            val newContact = ContactModel(contact.id, contact.name, contact.numbers, isSelected = true)
//            contact.isSelected = true
            selectedContacts.add(newContact)
            contactsList.remove(contact)
            contactsList.add(newContact)
            Log.d("contactsFetch", "list ${selectedContacts.toList()}")
            Log.d("contactsFetch", "list ${contactsList.toList()}")

        }
        else{
            Log.d("contactsFetch", "to be removed $contact")
            val newContact = ContactModel(contact.id, contact.name, contact.numbers)

//            contact.isSelected = false

            selectedContacts.remove(contact)
            contactsList.remove(contact)
            contactsList.add(newContact)
        }

        contactsList.sortBy { it.id }
//        contactsList = contactsList
    }
//if(selectedContacts.isNullOrEmpty())
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier

                .padding(16.dp)
//                .background(Color.Red)
        ) {
            items(contactsList, key = { "${it.id}${it.isSelected}" }) { contact ->
                ContactItem(contact, addToList = { onSelectContact(contact)} )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if(showDialog){
            ContactAlertDialog(
                selectedContactNames = selectedContacts.map { it.name },
                onConfirm = {
                    showDialog=false
                    saveNewStateInStore()
                            },
                onCancel = { showDialog=false }
            )
        }

        if(selectedContacts.isNotEmpty()) {
            FloatingActionButton(
                onClick = {
//                    Toast.makeText(context, "test", Toast.LENGTH_LONG).show()
                    showDialog = true
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 100.dp, height = 56.dp)
                    .align(Alignment.BottomCenter),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Text(text = "Confirm")
            }
        }else{
            Text(text = "*at least one contact should be selected",modifier = Modifier
                    .padding(16.dp)
//                .size(width = 100.dp, height = 56.dp)
                .align(Alignment.BottomCenter),)
        }
    }

//Spacer(modifier = Modifier.height(8.dp))
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        items(selectedContacts) { contact ->
//            ContactItem(contact = contact)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    }
//    selectedContacts?.get(0)?.let { ContactItem(contact = it) }

}



@Composable
fun ContactItem(contact: ContactModel, addToList: (()->Unit)? = null) {
    val context = LocalContext.current
    Log.d("contactsFetch", "$contact")
    val borderColor = if (contact.isSelected) {
        Log.d("contactsFetch", "bluee $contact")
        Color.Blue
    } else {
        MaterialTheme.colorScheme.error
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { addToList?.let { it() } }
            .padding(8.dp)
            .border(2.dp, borderColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row(verticalAlignment = Alignment.CenterVertically,) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(
//                painter = painterResource(id = R),
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = MaterialTheme.shapes.small)
                        .padding(2.dp)
                )
            }
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
                    modifier = Modifier.clickable { dial(context, contact.numbers[0].orEmpty()) }

                )
            }
        }
        if(contact.isSelected) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_contact_emergency_24),
//                imageVector = Icons.Default.,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
//                    .background(Color.Gray, shape = MaterialTheme.shapes.small)
                        .padding(2.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
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