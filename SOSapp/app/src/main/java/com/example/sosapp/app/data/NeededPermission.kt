package com.example.sosapp.app.data

enum class NeededPermission(
    val permission: String,
    val title: String,
    val description: String,
    val permanentlyDeniedDescription: String,
) {
    ACCESS_FINE_LOCATION(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
        title = "Read Location Permission",
        description = "This permission is needed to read your location. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your location. Please grant the permission in app settings.",
    ),
    ACCESS_COARSE_LOCATION(
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION,
        title = "Read Location Permission",
        description = "This permission is needed to read your location. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your location. Please grant the permission in app settings.",
    ),
    READ_CONTACTS(
        permission = android.Manifest.permission.READ_CONTACTS,
        title = "Read Contacts Permission",
        description = "This permission is needed to read your contacts. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your contacts. Please grant the permission in app settings.",
    ),
    SEND_SMS(
        permission = android.Manifest.permission.SEND_SMS,
        title = "Send SMS Permission",
        description = "This permission is needed to send sms to your emergency contacts. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to send sms to your emergency contacts. Please grant the permission in app settings.",
    ),
    CALL_PHONE(
        permission = android.Manifest.permission.CALL_PHONE,
        title = "Phone calling Permission",
        description = "This permission is needed to call the emergency number (911). Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to call the emergency number (911). Please grant the permission in app settings.",
    );

    fun permissionTextProvider(isPermanentDenied: Boolean): String {
        return if (isPermanentDenied) this.permanentlyDeniedDescription else this.description
    }
}

fun getNeededPermission(permission: String): NeededPermission {
    return NeededPermission.values().find { it.permission == permission } ?: throw IllegalArgumentException("Permission $permission is not supported")
}