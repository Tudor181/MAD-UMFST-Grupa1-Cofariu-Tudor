package com.example.sosapp.feature.SOSbutton.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.sosapp.R

@Composable
fun SOSAlertDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(

        onDismissRequest = onCancel,
        icon = { Row(){
            Icon(imageVector = Icons.Default.Phone, contentDescription = null)
            Icon(painter = painterResource(id = R.drawable.baseline_emergency_24), contentDescription = null)

        } },
        title = { Text(text = "SOS",textAlign = TextAlign.Center) },
        titleContentColor = Color.Red,
        text = {
            Text(text = "Accepting this will call the emergency number (911) and also send your location to your emergency contacts",)
        },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onCancel, colors = ButtonDefaults.buttonColors(Color.Green)) {
                Text(text = "Cancel")
            }
        }
    )
}
