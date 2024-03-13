package com.example.lab5intentstest.components

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.lab5intentstest.R

@Composable
fun ContactsList() {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
//            .background(Color.Red),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.yzf_r7), contentDescription =null )

        Button(onClick = {
            (ctx as Activity).finish()
        }) {
            Text(text = "Back to calculator")
        }
    }
}