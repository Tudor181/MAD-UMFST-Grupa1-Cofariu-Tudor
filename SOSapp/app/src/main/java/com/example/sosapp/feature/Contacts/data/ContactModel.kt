package com.example.sosapp.feature.Contacts.data
 data class ContactModel(
    val id:String="",
    val name: String="",
    val numbers: List<String> = emptyList(),
    val isSelected:Boolean = false,
)