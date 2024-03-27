package com.example.sosapp.feature.places.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sosapp.feature.places.data.LocationModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSearchDropdown(searchResults:List<LocationModel>, onSearch:(String)->Unit, onPlaceSelected:(LocationModel)->Unit, onClearLocation:()->Unit) {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .height(150.dp)
            .padding(32.dp)
//            .background(Color.Red)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                textStyle= TextStyle(fontWeight = FontWeight.W900),
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text(text = "Search... ") },
                trailingIcon = { Row {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    if(selectedText.isNotEmpty()) {
                        Icon(imageVector = Icons.Default.Clear,
                            contentDescription = "clear",
                            modifier = Modifier.clickable {
                                selectedText = ""
                                expanded = false
                                onClearLocation()
                            }
                        )
                    }
                }
                },
                leadingIcon = { Icon(
                    imageVector = Icons.Default.Search ,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onSearch(selectedText)
                        expanded = true
                    }
                )},
                modifier = Modifier.menuAnchor(),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White, // Change cursor color
                    containerColor = Color.White.copy(alpha = 0.7f),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(25)

            )

//            val filteredOptions =
//                searchResults.filter { it.contains(selectedText, ignoreCase = true) }
            if (searchResults.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                    }
                ) {
                    searchResults.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.placeName) },
                            onClick = {
                                selectedText = item.placeName
                                expanded = false
                                Toast.makeText(context, item.placeName, Toast.LENGTH_SHORT).show()
                                onPlaceSelected(item)
                            }
                        )
                    }
                }
            }

        }
//        Button(
//            onClick = { Toast.makeText(context, "clear", Toast.LENGTH_SHORT).show() },
//        ) {
//            Text(
//                modifier = Modifier.align(Alignment.BottomStart),
//                text = "clear search"
//            )
//        }
    }
}