package com.example.booklab4

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

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
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Kawasaki",
                icon = Icons.Filled.Place,
                route = Screens.Kawa.route
            ),
            BottomNavigationItem(
                label = "Honda",
                icon = Icons.Filled.Place,
                route = Screens.Honda.route
            ),
            BottomNavigationItem(
                label = "Yamaha",
                icon = Icons.Filled.Place,
                route = Screens.Yamaha.route
            ),
        )
    }
}