package com.example.sosapp.app.ui.screens

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.sosapp.R
import com.example.sosapp.app.data.NeededPermission
import com.example.sosapp.app.utils.CheckPermission
import com.example.sosapp.feature.Contacts.ui.goToAppSetting
import com.example.sosapp.feature.SOSbutton.ui.component.SOSbuttonComponent
import com.example.sosapp.feature.SOSbutton.utils.SOSAlertDialog
import com.example.sosapp.feature.SOSbutton.utils.SOShandler
import com.example.sosapp.feature.VehicleDiagnose.ui.components.VehicleDiagnoseButton
import com.example.sosapp.feature.supplies.ui.components.SuppliesButton
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject


@Composable

fun MainScreen(navController: NavController, selectedBottomNavigationItem: MutableState<Int>, currentLocation: MutableState<LatLng>) {
    val context = LocalContext.current

    val permissionsNeeded = NeededPermission.values().map { it.permission }
//        NeededPermission.ACCESS_COARSE_LOCATION.permission,
//        NeededPermission.ACCESS_FINE_LOCATION.permission,
//        NeededPermission.SEND_SMS.permission)


    var showDialog = remember { mutableStateOf(false) }
    var grantedAccess by remember {
        mutableStateOf(false)
    }

    var requestAgain by remember {
        mutableStateOf(false)
    }

    val sosHandler = SOShandler


    fun navigateToVehicleDiagnoseScreen() {
        navController.navigate(Screens.VehicleDiagnoseScreen.route)
    }

    fun navigateToEmergencySuppliesScreen(){
        navController.navigate(Screens.EmergencySuppliesScreen.route)
    }


    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMaps ->
        val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            grantedAccess = true
        } else {
            requestAgain = true
        }
    }

    fun checkAllPermissions() {
        Log.d("SOSHandler", "checking permisssions")
        if (CheckPermission.checkMultiplePermission(context, permissionsNeeded.toTypedArray())) {
            grantedAccess = true
        } else {
            Log.d("SOSHandler", "not permisssions,${permissionsNeeded}")
            launcherMultiplePermissions.launch(permissionsNeeded.toTypedArray())
        }
    }

//    val nearbyServices = [{position:46,47, name:'Restaurant casablanca'},{}]


    LaunchedEffect(key1 = true) {
        checkAllPermissions()
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(8.dp),
    ) {
//        Image(modifier=Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.portrait_mode_lfwrqmeqd8nha1i0), contentDescription =null )
        if (!grantedAccess) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
            ) {
                Text(
                    "This app require multiple permissions to work. Please accept them!",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W900,
                    fontFamily = FontFamily.Monospace
                )
                Button(
                    onClick = {
                        checkAllPermissions()
                        if (!grantedAccess)
//                        launcherMultiplePermissions.launch(permissionsNeeded.toTypedArray())
                            (context as Activity).goToAppSetting()
                    }
                ) {
                    Text(
                        "Please go to settings and change it",
                    )
                }
            }
        } else {
            SOSbuttonComponent(modifierButton = Modifier.align(Alignment.Center), showDialog)
            VehicleDiagnoseButton(
                modifierButton = Modifier.align(Alignment.BottomCenter),
                onClick = { navigateToVehicleDiagnoseScreen() })
            SuppliesButton(
                modifierButton = Modifier.align(Alignment.TopCenter),
                onClick = { navigateToEmergencySuppliesScreen() })

        }


        if (showDialog.value) {
            SOSAlertDialog(
                onConfirm = {
                    sosHandler.handleUrgency(context, currentLocation.value)
                    showDialog.value = false
                },
                onCancel = { showDialog.value = false })
        }
    }
}
