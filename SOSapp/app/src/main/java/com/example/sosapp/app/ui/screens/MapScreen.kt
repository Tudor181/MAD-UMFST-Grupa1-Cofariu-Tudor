package com.example.sosapp.app.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sosapp.feature.GPS.ui.LocationPermissions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
//import com.example.rescue4x4.more.MoreScreen
//import com.example.rescue4x4.more.askforhelp.AskForHelp
//import com.example.rescue4x4.more.diagnosis.DTCScreen
//import com.example.rescue4x4.more.weather.Const.Companion.colorBg1
//import com.example.rescue4x4.more.weather.Const.Companion.colorBg2
//import com.example.rescue4x4.more.weather.Const.Companion.permissions
//import com.example.rescue4x4.more.weather.viewmodel.MainViewModel
//import com.example.rescue4x4.more.weather.viewmodel.STATE
//import com.example.rescue4x4.more.weather.views.ForecastSection
//import com.example.rescue4x4.more.weather.views.WeatherSection
//import com.example.rescue4x4.sos.SOSScreen
//import com.example.rescue4x4.ui.theme.Rescue4x4Theme

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose≥



    @Composable
    fun MapScreen(
        navController: NavController,
        selectedBottomNavigationItem: MutableState<Int>,
//        context: Context,
        currentLocation: LatLng,
        cameraPositionState: CameraPositionState,
        locationRequired:MutableState<Boolean>,
        startLocationUpdates: ()->Unit
    ) {
        val context = LocalContext.current
        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
        var properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
        var isSatelliteViewEnabled by remember { mutableStateOf(false) }
        var mapLoaded by remember { mutableStateOf(false) }


        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionMaps ->
            val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                locationRequired.value = true
                startLocationUpdates()
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        Column {
            if (!mapLoaded) {
                LocationPermissions (onGrantedPermission = {
                    mapLoaded = true
                    locationRequired.value = true
                    startLocationUpdates()
                })
                return
//                LinearProgressIndicator(
//                    modifier = Modifier.fillMaxWidth(),
//                    color = MaterialTheme.colorScheme.primary,
//                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    properties = properties,
                    uiSettings = uiSettings,
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
//                        if (permissions.all {
//                                ContextCompat.checkSelfPermission(
//                                    context,
//                                    it
//                                ) == PackageManager.PERMISSION_GRANTED
//                            }) {
                        if(mapLoaded)
                            startLocationUpdates()
//                            mapLoaded = true
//                        } else {
//                            launcherMultiplePermissions.launch(permissions)
//                        }

                    }
                ) {
                    Marker(state = MarkerState(position = currentLocation), title = "you", snippet = "u re here")
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                            text = "Satellite Mode",
                            color = if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface,
                            fontStyle = FontStyle.Italic
                        )
                        Switch(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 0.dp),
                            checked = isSatelliteViewEnabled,
                            onCheckedChange = {
                                isSatelliteViewEnabled = it
                                properties =
                                    properties.copy(mapType = if (it) MapType.SATELLITE else MapType.NORMAL)
                            },
                            thumbContent = {
                                Icon(
                                    imageVector = if(isSatelliteViewEnabled){
                                        Icons.Default.Check
                                    }else{
                                        Icons.Default.Close
                                    },
                                    contentDescription = null
                                )
                            }
                        )
                    }
//                    ScaleBar(
//                        cameraPositionState = cameraPositionState,
//                        modifier = Modifier
//                            .align(Alignment.End)
//                            .padding(8.dp),
//                        textColor =  if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface,
//                        lineColor =  if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface
//                    )
                }
            }
        }
    }


//    @Composable
//    fun ErrorSection(errorMessage: String) {
//        return Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = errorMessage, color = Color.White)
//        }
//    }
//
//    @Composable
//    fun LoadingSection() {
//        return Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            CircularProgressIndicator(color = Color.White)
//        }
//    }

