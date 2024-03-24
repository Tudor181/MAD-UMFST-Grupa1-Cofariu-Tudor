package com.example.sosapp.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.sosapp.app.ui.navigation.BottomNavigationBar
import com.example.sosapp.ui.theme.SOSappTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var locationRequired = mutableStateOf(false)

    override fun onResume() {
        super.onResume()
        if(locationRequired.value){
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let{
            fusedLocationClient?.removeLocationUpdates(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
//        TODO("Not yet implemented")
        locationCallback.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {

            var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
            val cameraPositionState: CameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = LatLng(location.latitude, location.longitude)

                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            currentLocation, cameraPositionState.position.zoom
                        )
                    }
                }
            }

            SOSappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigationBar(
                        currentLocation,
                        cameraPositionState,
                        locationRequired,
                        this::startLocationUpdates
                    )
                }
            }
        }
    }
}
