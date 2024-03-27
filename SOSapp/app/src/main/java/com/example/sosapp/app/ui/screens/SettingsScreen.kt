package com.example.sosapp.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sosapp.R
import com.example.sosapp.feature.Contacts.data.MyNameStorage
import com.example.sosapp.feature.myVehicle.data.MyVehicleStorage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(navController: NavController, selectedBottomNavigationItem: MutableState<Int>){

    val context = LocalContext.current

    var myName by remember {
        mutableStateOf("")
    }

    fun navigate(route:String){
        navController.navigate(route)
    }

    fun notImplementedSetting(){
        Toast.makeText(context, "Not yet implemented", Toast.LENGTH_SHORT).show()
    }

    val settingsList = listOf(
        Setting("Dark Theme", onItemClick = {notImplementedSetting()}),
        Setting("Notifications", onItemClick = {notImplementedSetting()}),
        Setting("Emergency Contacts", icon = Icons.Default.Call, onItemClick = {navigate(Screens.EmergencyContactsScreen.route)}),
        Setting("My Vehicle", image = R.drawable.my_veh,onItemClick = {navigate(Screens.MyVehicleScreen.route)}),

        // Add more settings as needed
    )

    fun getName(){
        val name = MyNameStorage.getName(context)
        if (name != null) {
            myName = name
        }
    }
    LaunchedEffect(key1 = true) {
        getName()
    }

    fun saveMyName(){
        if(!myName.isNullOrEmpty())
            MyNameStorage.saveName(context, myName)
    }

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
                    text = "Settings",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.W900
                )
            }

            LazyColumn {
                item {
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
                        value = myName,
                        label = { Text(text = "My name") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { value ->
                            myName = value
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                saveMyName()
                            }
                        )

//                        onImeActionPerformed = { action:ImeAction, softwareController:SoftwareKeyboardController ->
//                            if (action == ImeAction.Done || action == ImeAction.Send) {
//                                // Perform the desired action when the user presses "Done" or leaves the keyboard
//                                // For example, you can hide the keyboard or perform validation
//                                softwareController.hide()
//                            }
//                        }

                    )
                }
                items(settingsList) { setting ->

                    SettingItem(
                        setting = setting,
                        onItemClick = { setting.onItemClick?.let { it() } }

                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }

}

@Composable
fun SettingItem(setting: Setting, onItemClick: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        if(setting.image != null) {
            Icon(
//            if(setting.image != null) painter = else
//            imageVector = setting.icon,
                painter = painterResource(id = setting.image),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
        }
        else{
            Icon(
                imageVector = setting.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = setting.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

data class Setting(
    val name: String,
    val icon: ImageVector = Icons.Default.Build,
    val image: Int? = null,
    val onItemClick: (() -> Unit)? = null
)

