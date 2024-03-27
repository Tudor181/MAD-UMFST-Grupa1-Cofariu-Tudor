// SOShandler.kt

package com.example.sosapp.feature.SOSbutton.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sosapp.app.data.NeededPermission
import com.example.sosapp.feature.Contacts.data.ContactModel
import com.example.sosapp.feature.Contacts.data.ContactStorage
import com.example.sosapp.feature.Contacts.data.MyNameStorage
import com.example.sosapp.feature.myVehicle.data.MyVehicleModel
import com.example.sosapp.feature.myVehicle.data.MyVehicleStorage
import com.google.android.gms.maps.model.LatLng

object SOShandler {
    private const val TAG = "SOShandler"
    private const val emergencyNumber = "0745156083"

    // Function to send SOS messages to saved contacts
    fun handleUrgency(context: Context, currentLocation: LatLng){
        sendSOSMessage(context, currentLocation)
        makePhoneCall(context, emergencyNumber)
    }
    fun sendSOSMessage(context: Context, currentLocation:LatLng) {
        val contactStorage = ContactStorage
        val savedContacts = contactStorage.getContacts(context)
        var savedVehicle = MyVehicleStorage.getVehicle(context)
        var name = MyNameStorage.getName(context)
        if(name == null){
            name=""
        }
        if(savedVehicle==null){
            savedVehicle= MyVehicleModel()
        }
        if(hasContactsPermission(context)) {
            // Iterate through saved contacts and send SOS message to each one
            for (contact in savedContacts) {
//                Log.d(TAG, "Sending SOS message to ${contact.name} (${contact.numbers}) ${currentLocation}")
                // Implement your logic to send SOS message to the contact
                sendSOSMessageToContact(context, contact, currentLocation, savedVehicle, name)
            }
        }
    }

    private fun hasContactsPermission(context: Context): Boolean {
//        return ContextCompat.checkSelfPermission(
//            context,
//            NeededPermission.READ_CONTACTS.permission
//        ) == PackageManager.PERMISSION_GRANTED
        return true
    }

    private fun sendSOSMessageToContact(
        context: Context,
        contact: ContactModel,
        currentLocation: LatLng,
        vehicle:MyVehicleModel,
        myName:String
    ) {
        // Check if there is at least one number associated with the contact
        if (contact.numbers.isNotEmpty()) {
            Log.d("testMessage", "before message")

            val message1 = "SOS message for ${contact.name} from $myName: Please help!" +
                    "\nCurrent location: maps.google.com/?q=${currentLocation.latitude},${currentLocation.longitude}\n"
            val message2 = "Details of the car in need\nNr of reg: ${vehicle.nrOfReg}\nManufacturer: ${vehicle.manufacturer}\nModel: ${vehicle.model}"
            Log.d("testMessage", "message:$message1")
            Log.d("testMessage", "message:$message2")

            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(contact.numbers[0],null,message1,null,null)
            smsManager.sendTextMessage(contact.numbers[0],null,message2,null,null)


            // Create an SMS intent
//            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
//                data = Uri.parse("smsto:${contact.numbers[0]}") // Use the first phone number
//                putExtra("sms_body", message)
//            }
//             Verify if there is an activity that can handle the SMS intent
//            if (smsIntent.resolveActivity(context.packageManager) != null) {
//                // Start the SMS intent
//                context.startActivity(smsIntent)
//            } else {
//                // Handle the case where there is no app to handle SMS
//                Toast.makeText(context, "No app to handle SMS", Toast.LENGTH_SHORT).show()
//            }
        } else {
            // Handle the case where there are no phone numbers associated with the contact
            Toast.makeText(context, "No phone number available for ${contact.name}", Toast.LENGTH_SHORT).show()
        }
    }

    fun makePhoneCall(context: Context, phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(callIntent)
    }
}
