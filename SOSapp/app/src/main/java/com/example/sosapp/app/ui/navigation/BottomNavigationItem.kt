package com.example.sosapp.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sosapp.app.ui.screens.Screens

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.MainScreen.route
            ),
            BottomNavigationItem(
                label = "Map",
                icon = Icons.Filled.Place,
                route = Screens.MapScreen.route
            ),
            BottomNavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                route = Screens.SettingsScreen.route
            ),
//            BottomNavigationItem(
//                label = "Honda",
//                icon = Icons.Filled.Place,
//                route = Screens.Honda.route
//            ),
//            BottomNavigationItem(
//                label = "Yamaha",
//                icon = Icons.Filled.Place,
//                route = Screens.Yamaha.route
//            ),
        )
    }
}