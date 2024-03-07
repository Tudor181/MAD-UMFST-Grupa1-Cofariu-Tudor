package com.example.booklab4

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get

@Composable
fun BottomNavigationBar() {
//initializing the default selected item
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val selectedState = remember { mutableStateOf(navigationSelectedItem) }
    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    val navController = rememberNavController()

//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == selectedState.value,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            selectedState.value = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.get(Screens.Home.route).id) {
//                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },

                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.Home.route) {
                //call our composable screens here
                HomePage(navController, selectedState = selectedState)
            }
            composable(Screens.Kawa.route) {
                //call our composable screens here
                Kawasaki(navController, selectedState = selectedState)
            }
            composable(Screens.Honda.route) {
                //call our composable screens here
                Honda(navController, selectedState = selectedState)
            }
            composable(Screens.Yamaha.route) {
                //call our composable screens here
                Yamaha(navController, selectedState = selectedState)
            }
        }
    }
}