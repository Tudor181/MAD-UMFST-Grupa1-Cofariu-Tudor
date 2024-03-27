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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sosapp.R
import com.example.sosapp.feature.Contacts.data.ContactModel
import com.example.sosapp.feature.VehicleDiagnose.data.DiagnoseModel
import com.example.sosapp.feature.VehicleDiagnose.utlis.DiagnoseStorage
import com.example.sosapp.feature.supplies.data.SupplyModel
import com.example.sosapp.feature.supplies.utils.SuppliesLocalProvider
import com.example.sosapp.feature.supplies.utils.SuppliesStorage

@Composable
fun EmergencySuppliesScreen(navController: NavController){

    val context = LocalContext.current

    var supplySearch by remember { mutableStateOf("") }

    val filterSupplies = remember {
        mutableStateListOf<SupplyModel>()
    }

    val allSupplies = remember {
        mutableStateListOf<SupplyModel>()
    }

    fun filter(){
        filterSupplies.clear()
        val upperCaseSupplyEntered = supplySearch.uppercase()
        filterSupplies.addAll(allSupplies.filter {
                supply -> supply.name.uppercase().contains(upperCaseSupplyEntered)
        })
        filterSupplies.sortBy { it.name }
    }

    fun onCheckedItem(item:SupplyModel){
        if(allSupplies.find { it.name === item.name  } != null) {
            val newSupplyModel = item.copy(isSelected = !item.isSelected)
            allSupplies.remove(item)
            allSupplies.add(newSupplyModel)
            allSupplies.sortBy { it.name }
            SuppliesLocalProvider.saveSupplies(context, allSupplies)
            filter()
        }
    }


    LaunchedEffect(key1 = supplySearch){
//        if(supplySearch.length > 0)
        filter()
    }

    LaunchedEffect(key1 = true ){


        Log.d("searchDTC", "codeentered $supplySearch")

        val suppliesStorage = SuppliesLocalProvider.getSupplies(context)
        if(suppliesStorage.isNullOrEmpty()) {
            Log.d("TEST", "e goaal")
            val upperCaseSupplyEntered = supplySearch.uppercase()
            val supplies = SuppliesStorage.searchMultipleSupplies(context, upperCaseSupplyEntered)
            allSupplies.clear()

            if (!supplies.isNullOrEmpty()) {
                allSupplies.sortBy { it.name }
                allSupplies.addAll(supplies)
                SuppliesLocalProvider.saveSupplies(context, supplies)
                filter()
            }
        }
        else{
            allSupplies.clear()
            allSupplies.addAll(suppliesStorage)
            filter()
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
                    text = "Supplies",
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
                        value = supplySearch,
                        label = { Text(text = "Supply") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { value ->
                            supplySearch = value
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
                items(filterSupplies) { supply ->

                  SupplyItem(supply, addToList = { onCheckedItem(supply) })
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                FloatingActionButton(
                    onClick = {
                        Toast.makeText(context, "Not yet implemented", Toast.LENGTH_LONG).show()
//                    refreshCameraPositionState()
//                        addNewItem
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(56.dp),
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.onBackground
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new supply",
                        modifier = Modifier.padding(16.dp),
                        tint = Color.Green
                    )
                }
            }
        }
    }
}


@Composable
fun SupplyItem(supply: SupplyModel, addToList: (()->Unit)? = null) {
    val context = LocalContext.current
//    Log.d("contactsFetch", "$supply")
    val borderColor = if (supply.isSelected) {
//        Log.d("contactsFetch", "bluee $supply")
        Color.Blue
    } else {
        MaterialTheme.colorScheme.error
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { addToList?.let { it() } }
            .padding(8.dp)
            .border(2.dp, borderColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row(verticalAlignment = Alignment.CenterVertically,) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(
                painter = painterResource(id = R.drawable.baseline_article_24),
//                    imageVector = Icons.Default.b,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
//                        .background(Color.Gray, shape = MaterialTheme.shapes.small)
                        .padding(2.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = supply.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

            }
        }
        if(supply.isSelected) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(
                imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

