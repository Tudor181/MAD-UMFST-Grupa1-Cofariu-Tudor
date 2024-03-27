package com.example.sosapp.feature.supplies.ui.components


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuppliesButton(modifierButton:Modifier,onClick:()->Unit){
    Button(
        modifier = modifierButton,
//            .size(200.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(Color.Blue),
        shape = RoundedCornerShape(percent = 20),
        elevation = ButtonDefaults.buttonElevation(4.dp),

        ) {
        Text(
            "Supplies list",
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.Monospace
        )
    }
}