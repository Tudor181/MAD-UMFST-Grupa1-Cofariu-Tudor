package com.example.sosapp.feature.GPS.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.sosapp.app.data.NeededPermission
import com.example.sosapp.common.PermissionAlertDialog


@Composable
fun LocationPermissions(
    onGrantedPermission: ()->Unit
) {

    val activity = LocalContext.current as Activity
    var grantedAccess by remember {
        mutableStateOf(false)
    }

    val permissions = arrayOf(
        NeededPermission.ACCESS_FINE_LOCATION.permission,
        NeededPermission.ACCESS_COARSE_LOCATION.permission,
    )

    val permissionDialog = remember {
        mutableStateListOf<NeededPermission>()
    }
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMaps ->
        val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            onGrantedPermission()
//            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()

        } else {
//            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            permissionDialog.add(NeededPermission.ACCESS_FINE_LOCATION)
        }
    }

    val contactsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted)
                permissionDialog.add(NeededPermission.ACCESS_FINE_LOCATION)
            else onGrantedPermission()
        }
    )

    if(!grantedAccess) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.CenterVertically
            )
        ) {
//            Button(
//                onClick = {
//                    contactsPermissionLauncher.launch(NeededPermission.READ_LOCATION.permission)
//                }
//            ) {
//                Text(text = "Request contacts permission")
//            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                Button(onClick = {
//                    val permissionCheckResult = ContextCompat.checkSelfPermission(
//                        activity as Context,
//                        NeededPermission.READ_LOCATION.permission
//                    )
                    val permissionsCheckResult = permissions.all {
                        ContextCompat.checkSelfPermission(
                            activity as Context,
                            it
                        ) == PackageManager.PERMISSION_GRANTED
                    }

                    if (permissionsCheckResult) {
                        grantedAccess = true
                        onGrantedPermission()
                    } else {
                        // Request a permission
                        launcherMultiplePermissions.launch(permissions)

//                        contactsPermissionLauncher.launch(NeededPermission.READ_LOCATION.permission)
                    }

                }) {
                    Text(text = "Show Map")
                }

            }
        }
    }

    permissionDialog.forEach { permission ->
        PermissionAlertDialog(
            neededPermission = permission,
            onDismiss = { permissionDialog.remove(permission) },
            onOkClick = {
                permissionDialog.remove(permission)
            },
            onGoToAppSettingsClick = {
                permissionDialog.remove(permission)
                activity.goToAppSetting()
            },
            isPermissionDeclined = !activity.shouldShowRequestPermissionRationale(permission.permission)
        )
    }
}


fun Activity.goToAppSetting() {
    val i = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(i)
}