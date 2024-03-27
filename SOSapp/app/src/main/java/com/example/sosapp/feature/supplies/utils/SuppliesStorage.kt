package com.example.sosapp.feature.supplies.utils

import android.content.Context
import com.example.sosapp.feature.VehicleDiagnose.data.DiagnoseModel
import com.example.sosapp.feature.supplies.data.SupplyModel
import org.json.JSONObject

object SuppliesStorage{
    fun searchDtcDescription(context: Context, nameSearch: String): SupplyModel? {
        // read the contents of the dtc-codes.json file from assets
        val jsonString =
            context.assets.open("supplies.json").bufferedReader().use { it.readText() }
        // convert the json string into a JSONObject
        val jsonObject = JSONObject(jsonString)
        // get the json array containg the dtc codes
        val supplyArray = jsonObject.getJSONArray("emergency_supplies")

        val supplyArray2 = Array(supplyArray.length()) { index ->
            val dtcObject = supplyArray.getJSONObject(index)
            SupplyModel(
                name = dtcObject.getString("name"),
                description = dtcObject.getString("description")
            )
        }
        return supplyArray2.find { it.name == nameSearch }
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
    fun searchMultipleSupplies(context: Context, nameSearch: String): List<SupplyModel>? {
        // read the contents of the dtc-codes.json file from assets
        val jsonString =
            context.assets.open("supplies.json").bufferedReader().use { it.readText() }
        // convert the json string into a JSONObject
        val jsonObject = JSONObject(jsonString)
        // get the json array containg the dtc codes
        val supplyArray = jsonObject.getJSONArray("emergency_supplies")

//        val dtcArray2 = Array(dtcArray.length()) { index ->
//            val dtcObject = dtcArray.getJSONObject(index)
//            DiagnoseModel(
//                code = dtcObject.getString("code"),
//                description = dtcObject.getString("description")
//            )
//        }
        val supplyArray2 = Array(supplyArray.length()) { index ->
            val dtcObject = supplyArray.getJSONObject(index)
            SupplyModel(
                name = dtcObject.getString("name"),
                description = dtcObject.getString("description")
            )
        }
        return supplyArray2.filter { supply -> supply.name.uppercase().contains(nameSearch) }
//
    }
}