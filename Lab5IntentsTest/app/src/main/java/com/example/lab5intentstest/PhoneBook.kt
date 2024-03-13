package com.example.lab5intentstest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.lab5intentstest.components.Permissions
import com.example.lab5intentstest.helpers.PermisionHelper
import com.example.lab5intentstest.ui.theme.Lab5IntentsTestTheme
import java.util.Objects

class PhoneBook : ComponentActivity() {

    private val contactsProvider by lazy {
        ContactsProvider(context = this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5IntentsTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Permissions(contactsProvider)
                }
            }
        }
    }


    private val CONTACTS_PERMISSION_REQUEST_CODE = 123

    private fun requestContactsPermission(ctx: Context) {
        if (ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                ctx as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                CONTACTS_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted
            // Continue with your app logic
        }
    }
}
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == CONTACTS_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, continue with your app logic
//            } else {
//                // Permission denied, handle accordingly
//            }
//        }
//    }






@Composable
fun test() {

    val context = LocalContext.current
//    val file = context
//    val uri = FileProvider.getUriForFile(
//        Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
//    )

    var capturedImageUri by remember {
        mutableStateOf<String>("")
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
//            capturedImageUri = uri
        }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
//            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            Toast.makeText(context,"Test", Toast.LENGTH_SHORT).show()

//            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }

    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
        Button(onClick = {
            val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)

            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//                cameraLauncher.launch(uri)
                capturedImageUri = "SETED"
            } else {
                // Request a permission
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }

        }) {
            Text(text = "Open Camera")
        }

        if (capturedImageUri.isNotEmpty() == true) {
            Image(
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
                    .size(400.dp),
                painter = painterResource(R.drawable.yzf_r7),
                contentDescription = null
            )
        }
    }
}




