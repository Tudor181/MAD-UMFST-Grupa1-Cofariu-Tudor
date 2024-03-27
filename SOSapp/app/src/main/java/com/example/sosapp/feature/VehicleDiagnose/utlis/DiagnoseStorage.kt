package com.example.sosapp.feature.VehicleDiagnose.utlis

import android.content.Context
import com.example.sosapp.feature.VehicleDiagnose.data.DiagnoseModel
import org.json.JSONObject

object DiagnoseStorage{
    fun searchDtcDescription(context: Context, dtcCode: String): DiagnoseModel? {
        // read the contents of the dtc-codes.json file from assets
        val jsonString =
            context.assets.open("dtc-codes.json").bufferedReader().use { it.readText() }
        // convert the json string into a JSONObject
        val jsonObject = JSONObject(jsonString)
        // get the json array containg the dtc codes
        val dtcArray = jsonObject.getJSONArray("dtcs")

        val dtcArray2 = Array(dtcArray.length()) { index ->
            val dtcObject = dtcArray.getJSONObject(index)
            DiagnoseModel(
                code = dtcObject.getString("code"),
                description = dtcObject.getString("description")
            )
        }
        return dtcArray2.find { it.code == dtcCode }
//    val test = JSON
        // iterate over the dtc codes and find the description of the given dtc code
//        for (i in 0 until dtcArray.length()) {
//            val dtcObject = dtcArray.getJSONObject(i)
//            // check if the code of the dtc object is equal to the given dtc code
//            if (dtcObject.getString("code") == dtcCode) {
//                // return the description of the dtc code if found
//                return dtcObject.getString("description")
//            }
//        }
//        return null
    }
    fun searchMultipleDtcDescription(context: Context, dtcCode: String): List<DiagnoseModel>? {
        // read the contents of the dtc-codes.json file from assets
        val jsonString =
            context.assets.open("dtc-codes.json").bufferedReader().use { it.readText() }
        // convert the json string into a JSONObject
        val jsonObject = JSONObject(jsonString)
        // get the json array containg the dtc codes
        val dtcArray = jsonObject.getJSONArray("dtcs")

        val dtcArray2 = Array(dtcArray.length()) { index ->
            val dtcObject = dtcArray.getJSONObject(index)
            DiagnoseModel(
                code = dtcObject.getString("code"),
                description = dtcObject.getString("description")
            )
        }
        return dtcArray2.filter { dtc -> dtc.code.contains(dtcCode) }
//
    }
}