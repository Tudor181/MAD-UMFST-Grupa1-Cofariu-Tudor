package com.example.sosapp.app.ui.screens

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
import com.example.sosapp.app.ui.navigation.BottomNavigationItem

@Composable
fun MainScreen(navController: NavController, selectedBottomNavigationItem: MutableState<Int>){
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
            text = "Current Index:${selectedBottomNavigationItem.value}",
            color = Color.Blue,
            fontSize = 20.sp,
        )
        Button(
            onClick = {
                selectedBottomNavigationItem.value++
//                navController.navigate(Screens.Kawa.route)
            }
        ) {
            Text("Get started with First Chapter! (Kawasaki)", fontSize = 15.sp)
        }
    }
}