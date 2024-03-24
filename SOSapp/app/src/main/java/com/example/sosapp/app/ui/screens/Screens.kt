package com.example.sosapp.app.ui.screens

sealed class Screens(val route : String) {
    object MainScreen : Screens("MainScreen")
    object MapScreen : Screens("MapScreen")

//
//    object Kawa : Screens("KawaPage")
//    object Honda : Screens("HondaPage")
//    object Yamaha : Screens("YamahaPage")
}