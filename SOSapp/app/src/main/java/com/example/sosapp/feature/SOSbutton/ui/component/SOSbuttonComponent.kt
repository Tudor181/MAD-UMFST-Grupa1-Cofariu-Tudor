package com.example.sosapp.feature.SOSbutton.ui.component


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SOSbuttonComponent(modifierButton:Modifier,showDialog: MutableState<Boolean>){
    Button(
        modifier = modifierButton
//            .align(Alignment.Center)
            .size(100.dp),
        onClick = {
            showDialog.value = true
        },
        colors = ButtonDefaults.buttonColors(Color.Red),
        shape = RoundedCornerShape(percent = 50),
        elevation = ButtonDefaults.buttonElevation(4.dp),

        ) {
        Text(
            "SOS",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.W900,
            fontFamily = FontFamily.Monospace
        )
    }
}