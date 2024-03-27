package com.example.sosapp.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sosapp.R
import com.example.sosapp.feature.myVehicle.data.MyVehicleModel
import com.example.sosapp.feature.myVehicle.data.MyVehicleStorage


@Composable
fun MyVehicleScreen(navController: NavController) {
    val context = LocalContext.current

    var showEditFields by remember {
        mutableStateOf(false)
    }
    var myVeh = remember {
        mutableStateOf<MyVehicleModel>(MyVehicleModel())
    }

    fun getVeh(){
        val veh = MyVehicleStorage.getVehicle(context)
        if (veh != null) {
            myVeh.value = veh
        }
    }
    LaunchedEffect(key1 = true) {
        getVeh()
    }

    fun editVehicle(){
        MyVehicleStorage.saveMyVehicle(context, myVeh.value)
        showEditFields=false
        getVeh()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "My Vehicle",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.W900
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.yzf_r7_photo),
                contentDescription = null,
//                modifier = Modifier.size(400.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            if (!showEditFields && (myVeh.value.nrOfReg.isNullOrEmpty() || myVeh.value.manufacturer.isNullOrEmpty() || myVeh.value.model.isNullOrEmpty())) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Your vehicle is not registered.",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showEditFields = true }) {
                        Text(text = "Register", fontWeight = FontWeight.Bold)
                    }
                }

            } else if (!showEditFields) {
//
//                Text(
//                    text = "Nr of registration: ${myVeh.value.nrOfReg ?: "please register"}",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//                Text(
//                    text = "Manufacturer: ${myVeh.value.manufacturer}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Text(
//                    text = "Model: ${myVeh.value.model}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
                Column(modifier= Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                    VehicleDetail(label = "Nr of registration:", value = myVeh.value.nrOfReg ?: "please register")
                    VehicleDetail(label = "Manufacturer:", value = myVeh.value.manufacturer ?: "unknown")
                    VehicleDetail(label = "Model:", value = myVeh.value.model ?: "unknown")

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Button(onClick = { showEditFields = true }) {
                            Text(text = "Edit vehicle", fontWeight = FontWeight.Bold)
                        }
                    }
                }


            }
            if (showEditFields) {
                nrOfRegField(myVeh)
                manufacturerField(myVeh)
                modelField(myVeh)
                Button(onClick = { editVehicle() }) {
                    Text(text = "Done", fontWeight = FontWeight.Bold)
                }
            }


        }

    }
}

@Composable
fun VehicleDetail(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun nrOfRegField(myVeh:MutableState<MyVehicleModel>){
    OutlinedTextField(
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(percent = 50),
        value = myVeh.value.nrOfReg,
        label = { Text(text = "Nr of reg") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { value ->
            myVeh.value = myVeh.value.copy(nrOfReg = value)
        }
    )
}
@Composable
fun manufacturerField(myVeh:MutableState<MyVehicleModel>){
    OutlinedTextField(
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(percent = 50),
        value = myVeh.value.manufacturer,
        label = { Text(text = "Manufacturer") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { value ->
            myVeh.value = myVeh.value.copy(manufacturer = value)
        }
    )
}
@Composable
fun modelField(myVeh:MutableState<MyVehicleModel>){
    OutlinedTextField(
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(percent = 50),
        value = myVeh.value.model,
        label = { Text(text = "Model") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { value ->
            myVeh.value = myVeh.value.copy(model = value)
        }
    )
}
