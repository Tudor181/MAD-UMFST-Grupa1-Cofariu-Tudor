package com.example.sosapp.app.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sosapp.feature.VehicleDiagnose.data.DiagnoseModel
import com.example.sosapp.feature.VehicleDiagnose.utlis.DiagnoseStorage

@Composable
fun VehicleDiagnoseScreen(navController: NavController){

    val context = LocalContext.current

    var codeEntered by remember { mutableStateOf("") }

    fun navigate(route:String){
        navController.navigate(Screens.EmergencyContactsScreen.route)
    }

    fun notImplementedSetting(){
        Toast.makeText(context, "Not yet implemented", Toast.LENGTH_SHORT).show()
    }

    val foundDiagnosis = remember {
        mutableStateListOf<DiagnoseModel>()
    }

    LaunchedEffect(key1 = codeEntered ){
        Log.d("searchDTC", "codeentered $codeEntered")
//        val diagnoseFound = DiagnoseStorage.searchDtcDescription(context, codeEntered)
//        Log.d("searchDTC", "diagnose ${diagnoseFound}")
//        foundDiagnosis.clear()
//        if(diagnoseFound != null){
//            foundDiagnosis.add(diagnoseFound)
//        }
        val upperCaseCodeEntered = codeEntered.uppercase()
        val diagnosis = DiagnoseStorage.searchMultipleDtcDescription(context,upperCaseCodeEntered)
        foundDiagnosis.clear()

        if(!diagnosis.isNullOrEmpty()){
            foundDiagnosis.addAll(diagnosis)
        }
    }

//
//    val settingsList = listOf(
//        Setting("Dark Theme", onItemClick = {notImplementedSetting()}),
//        Setting("Notifications", onItemClick = {notImplementedSetting()}),
//        Setting("Emergency Contacts", Icons.Default.Call, onItemClick = {navigate(Screens.EmergencyContactsScreen.route)}),
//        // Add more settings as needed
//    )

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Diagnose",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.W900
                )
            }

            LazyColumn {
                item {
                    OutlinedTextField(
                        singleLine = true,
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null)},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(percent = 50),
                        value = codeEntered,
                        label = { Text(text = "Please enter your error code") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { value ->
                            codeEntered = value
                        },


                    )
                    Spacer(modifier = Modifier.height(15.dp))

//                    Button(
//                        onClick = { /* Handle emergency contact list button click */ },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(text = "Emergency Contact List")
//                    }
                }
                items(foundDiagnosis) { diagnose ->

                    DiagnoseItem(
                        diagnose = diagnose,
                        onItemClick = {  }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}

@Composable
fun DiagnoseItem(diagnose: DiagnoseModel, onItemClick: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .border(1.dp, MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(50))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = MaterialTheme.shapes.small)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            Text(
                text = diagnose.code,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = diagnose.description,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

