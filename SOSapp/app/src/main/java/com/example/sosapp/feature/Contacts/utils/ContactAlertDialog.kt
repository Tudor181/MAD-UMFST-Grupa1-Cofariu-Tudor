package com.example.sosapp.feature.Contacts.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ContactAlertDialog(selectedContactNames: List<String>?, onConfirm: () -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = "Selected Contacts",textAlign = TextAlign.Center) },
        text = {
            selectedContactNames?.let { names ->
                Column {
                    names.forEach { name ->
                        Text(text = name)
                    }
                    Text(text = "Are u sure you want this to be your emergency contact(s)?",
                        textAlign = TextAlign.Center)
                }
            }

        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
        }
    )
}
