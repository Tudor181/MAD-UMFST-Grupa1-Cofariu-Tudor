package com.example.sosapp.app.ui.screens

sealed class Screens(val route : String) {
    object MainScreen : Screens("MainScreen")
    object MapScreen : Screens("MapScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object EmergencyContactsScreen : Screens("EmergencyContactsScreen")
    object VehicleDiagnoseScreen : Screens("VehicleDiagnoseScreen")
    object EmergencySuppliesScreen : Screens("EmergencySuppliesScreen")
    object MyVehicleScreen : Screens("MyVehicleScreen")


}