package com.example.sosapp.app.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sosapp.R
import com.example.sosapp.feature.Contacts.data.ContactModel
import com.example.sosapp.feature.GPS.ui.LocationPermissions
import com.example.sosapp.feature.places.data.LocationModel
import com.example.sosapp.feature.places.data.PlacesProvider
import com.example.sosapp.feature.places.ui.PlaceSearchDropdown
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
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
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.widgets.DisappearingScaleBar
import com.google.maps.android.compose.widgets.ScaleBar
import kotlinx.coroutines.delay

//import com.google.maps.android.compose≥



    @Composable
    fun MapScreen(
        navController: NavController,
        selectedBottomNavigationItem: MutableState<Int>,
        currentLocation: MutableState<LatLng>,
        cameraPositionState: CameraPositionState,
        locationRequired:MutableState<Boolean>,
        startLocationUpdates: ()->Unit
    ) {
        val context = LocalContext.current
        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
        var properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
        var isSatelliteViewEnabled by remember { mutableStateOf(false) }
//        var mapLoaded by remember { mutableStateOf(false) }
        var googleMapLoaded by remember { mutableStateOf(false) }
        var permissionsGranted by remember {
            mutableStateOf(false)
        }
        var firstLoad by remember {
            mutableStateOf(true)
        }

        val searchPlaceResults = remember { mutableStateListOf<LocationModel>() }
        var selectedPlaceWithDetails by remember {
            mutableStateOf<Place?>(null)
        }

        var selectedPlace by remember {
            mutableStateOf<LocationModel?>(null)
        }

        fun refreshCameraPositionState(){
            Log.d("mapLog", "${cameraPositionState.position}")
            Log.d("mapLog", "currentLocation${currentLocation.value}")

            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                currentLocation.value, cameraPositionState.position.zoom
            )
        }

        LaunchedEffect(key1 = googleMapLoaded, key2 = currentLocation.value, key3 = firstLoad){
            Log.d("mapLog", "launcheffect $googleMapLoaded")
            if(googleMapLoaded && firstLoad && currentLocation.value.latitude != 0.toDouble()){
                firstLoad = false
                Log.d("mapLog", "googleMapLoaded $googleMapLoaded")
                Log.d("mapLog", "${cameraPositionState.position}")
                refreshCameraPositionState()
                Log.d("mapLog", "${cameraPositionState.position}")

            }
        }

        LaunchedEffect(key1 = permissionsGranted, key2 = googleMapLoaded){
            Log.d("omMapLoaded", "launcheffect $permissionsGranted")
            if(permissionsGranted && googleMapLoaded){
                startLocationUpdates()
            }
        }

        fun onPermissionsLoaded(){
            locationRequired.value=true
            permissionsGranted = true
        }

        fun onSearchResults(results:List<LocationModel>){
            Log.d("places", "rezultate: $results")
            searchPlaceResults.clear()
            searchPlaceResults.addAll(results)
        }

        fun handleSearch(query:String){
//            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
            Log.d("PlacePrediction", "current ${currentLocation.value}")
            PlacesProvider.searchPlaces(query, currentLocation.value, context, ::onSearchResults)
        }

        fun onPlaceDetailedResult(result:LatLng){
            Log.d("places", "rezultate: $result")
//            searchPlaceResults.clear()
//            searchPlaceResults.addAll(results)
        }

        fun handlePlaceSelected(place:LocationModel){
//            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
            Log.d("PlacePrediction", "current ${currentLocation.value}")
//            PlacesProvider.getPlaceDetails(place.placeId, ::onPlaceDetailedResult)
            selectedPlace = place
            PlacesProvider.putMarkerOnMap(place.placeId, context, cameraPositionState) { place ->
//                placeSelectedLocation = place?.latLng
                selectedPlaceWithDetails = place
            }

        }

        fun onClearLocation(){
            selectedPlace = null
            selectedPlaceWithDetails = null
            searchPlaceResults.clear()
        }

        LocationPermissions (
            onCheckedPermissions = { onPermissionsLoaded() },
            onGrantedPermissions = {
                onPermissionsLoaded()
            }
        )

        Column {
            if (!googleMapLoaded || !permissionsGranted || firstLoad) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    properties = properties,
                    uiSettings = uiSettings,
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
                        googleMapLoaded = true
                    }
                ) {
                    Marker(state = MarkerState(position = currentLocation.value),
                        onClick = {marker->
                            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                            true
                        }
                    )
//                    Log.d("PlacePrediction", "show marker $selectedPlaceWithDetails")

                    if( selectedPlaceWithDetails?.latLng != null) {
                        MarkerInfoWindowContent(
                            state = MarkerState(position = selectedPlaceWithDetails!!.latLng!!),
                            title = "${selectedPlace?.placeName}",
                            snippet = "${selectedPlace?.placeAddress}"
                        ) { marker ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    modifier = Modifier.padding(top = 6.dp),
                                    text = marker.title ?: "",
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text("Full address: ${selectedPlace?.placeAddress}\nPhone Number: ${selectedPlaceWithDetails!!.phoneNumber}")
                                Text("Opening hours: ${selectedPlaceWithDetails?.rating}")
                                selectedPlaceWithDetails!!.openingHours?.weekdayText?.map {
                                    Text(
                                    text = it
                                ) }

//                                Image(
//                                    modifier = Modifier
//                                        .padding(top = 6.dp)
//                                        .border(
//                                            BorderStroke(3.dp, color = Color.Gray),
//                                            shape = RectangleShape
//                                        ),
//                                    painter = painterResource(id = R.drawable.yzf_r7_photo),
//                                    contentDescription = "A picture of selected location"
//                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    DisappearingScaleBar(
                        modifier = Modifier
                            .padding(top = 5.dp, end = 15.dp),
//                            .align(Alignment.BottomCenter),
                        cameraPositionState = cameraPositionState,
                        textColor =  if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.onBackground,
                        lineColor =  if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.onBackground
                    )
                    FloatingActionButton(
                        onClick = {
//                            Toast.makeText(context, "test", Toast.LENGTH_LONG).show()
                            refreshCameraPositionState()
                                  },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(56.dp),
                        shape = CircleShape,
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        Icon(
                            painter = painterResource(id =  R.drawable.baseline_my_location_24),
                            contentDescription = "Current Location",
                            modifier = Modifier.padding(16.dp),
                            tint = Color.Black
                        )
                    }
                }
                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Box(modifier = Modifier
                        .size(125.dp)
                        .padding(8.dp)) {
                        Text(
                            modifier = Modifier.align(
                                Alignment.TopCenter),
                            text = "Satellite Mode",
                            color = if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.onBackground,
                            fontStyle = FontStyle.Italic
                        )
//                        Text(text = "$currentLocation", color = Color.Red)
                        Switch(
                            modifier = Modifier
                                .align(
                                    Alignment.CenterEnd),
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
                }
                PlaceSearchDropdown(searchPlaceResults,::handleSearch, ::handlePlaceSelected, ::onClearLocation)
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


