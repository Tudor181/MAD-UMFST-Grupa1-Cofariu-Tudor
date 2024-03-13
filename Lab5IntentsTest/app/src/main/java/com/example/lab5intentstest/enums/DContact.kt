package com.example.lab5intentstest.enums

data class DContact(
    val id:String="",
    val name: String="",
    val numbers: List<String> = emptyList()
)