package com.example.sosapp.feature.Contacts.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.sosapp.app.ui.screens.PhoneContactsScreen
import com.example.sosapp.common.LoadingComponent
import com.example.sosapp.common.PermissionAlertDialog
import com.example.sosapp.feature.Contacts.data.ContactsProvider


@Composable
fun ContactsPermission(contactsProvider: ContactsProvider) {

    val activity = LocalContext.current as Activity
    var grantedAccess by remember {
        mutableStateOf(false)
    }

    val permissionDialog = remember {
        mutableStateListOf<NeededPermission>()
    }

    val contactsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
//            grantedAccess = true
            grantedAccess = true
            if (!isGranted)
                permissionDialog.add(NeededPermission.READ_CONTACTS)
        }
    )

    LaunchedEffect(key1 = true ){
        if(!grantedAccess){
            val permissionCheckResult = ContextCompat.checkSelfPermission(
                activity as Context,
                NeededPermission.READ_CONTACTS.permission
            )

            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                grantedAccess = true
            } else {
                // Request the permission
                contactsPermissionLauncher.launch(NeededPermission.READ_CONTACTS.permission)
            }
        }
    }

    if(grantedAccess){
        PhoneContactsScreen(contactsProvider = contactsProvider)
    }
    else {
        LoadingComponent()
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(
//                16.dp,
//                Alignment.CenterVertically
//            )
//        ) {
//            Button(
//                onClick = {
//                    contactsPermissionLauncher.launch(NeededPermission.READ_CONTACTS.permission)
//                }
//            ) {
//                Text(text = "Request contacts permission")
//            }
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.padding(12.dp)
//            ) {
//                Button(onClick = {
//                    val permissionCheckResult = ContextCompat.checkSelfPermission(
//                        activity as Context,
//                        Manifest.permission.READ_CONTACTS
//                    )
//
//                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//                        grantedAccess = true
//                    } else {
//                        // Request a permission
//                        contactsPermissionLauncher.launch(NeededPermission.READ_CONTACTS.permission)
//                    }
//
//                }) {
//                    Text(text = "Open Contacts")
//                }
//
//            }
//
//
//        }

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