package com.example.sosapp.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.sosapp.app.ui.screens.MainScreen
import com.example.sosapp.app.ui.screens.MapScreen
import com.example.sosapp.app.ui.screens.Screens
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

@Composable
fun BottomNavigationBar(currentLocation:LatLng, cameraPositionState: CameraPositionState, locationRequired:MutableState<Boolean>, startLocationUpdates:()->Unit) {
//initializing the default selected item
//    var navigationSelectedItem by remember {
//        mutableIntStateOf(0)
//    }

    val navigationSelectedItem = remember { mutableIntStateOf(0) }
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
                        selected = index == navigationSelectedItem.value,
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
                            navigationSelectedItem.value = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.get(Screens.MainScreen.route).id) {
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
            startDestination = Screens.MainScreen.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.MainScreen.route) {
                //call our composable screens here
                MainScreen(navController, selectedBottomNavigationItem = navigationSelectedItem)
            }
            composable(Screens.MapScreen.route) {
                //call our composable screens here
                MapScreen(navController, selectedBottomNavigationItem = navigationSelectedItem,currentLocation, cameraPositionState,locationRequired,startLocationUpdates  )
            }
//            composable(Screens.Honda.route) {
//                //call our composable screens here
//                Honda(navController, selectedState = selectedState)
//            }
//            composable(Screens.Yamaha.route) {
//                //call our composable screens here
//                Yamaha(navController, selectedState = selectedState)
//            }
        }
    }
}