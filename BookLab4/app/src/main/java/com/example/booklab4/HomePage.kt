package com.example.booklab4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun HomePage(navController: NavController, selectedState: MutableState<Int>){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text(
            text = "Hello! Welcome to Motorcycle Catalog",
            color = Color.Blue,
            fontSize = 20.sp,
        )
        Text(
            text = "Current Index:${selectedState.value}",
            color = Color.Blue,
            fontSize = 20.sp,
        )
        Button(
            onClick = {
                selectedState.value++
                navController.navigate(Screens.Kawa.route)
            }
            ) {
            Text("Get started with First Chapter! (Kawasaki)", fontSize = 15.sp)
        }
    }
}