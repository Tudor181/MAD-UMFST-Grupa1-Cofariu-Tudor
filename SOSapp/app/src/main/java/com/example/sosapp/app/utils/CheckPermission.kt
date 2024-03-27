package com.example.sosapp.app.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.sosapp.app.data.NeededPermission

object CheckPermission {

    fun checkSinglePermission(context: Context, permission: String): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            permission
        )
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    fun checkMultiplePermission(context: Context, permissions: Array<String>): Boolean {
        val permissionsCheckResult = permissions.all {
            this.checkSinglePermission(context, it)
        }

        return permissionsCheckResult
    }
}