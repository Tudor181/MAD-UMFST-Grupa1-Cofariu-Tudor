package com.example.sosapp.feature.places.data

import android.content.Context
import android.util.Log
import com.example.sosapp.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.CameraPositionState


object PlacesApiClient {
    private var initialized = false
    private lateinit var placesClient: PlacesClient

    fun initialize(context: Context) {
        if (!initialized) {
            Places.initialize(context, context.getString(R.string.google_maps_api_key))
            placesClient = Places.createClient(context)
            initialized = true
        }
    }

    fun getPlacesClient(): PlacesClient {
        if (!initialized) {
            throw IllegalStateException("PlacesApiClient must be initialized before use")
        }
        return placesClient
    }
}
object PlacesProvider {
    fun putMarkerOnMap(
        placeId: String,
        context: Context,
        cameraPositionState: CameraPositionState,
        onLocationReady: (Place?) -> Unit
    ) {
//        var location:LatLng? = null

        // get place details using the provided placeId and PlacesClient
        getPlaceDetails(placeId) { place:Place? ->
            place?.let {
//                location = latLng
                // if the latlng is not null, create a new cameraposition
                place.latLng?.let {
                    val cameraPosition = CameraPosition.Builder()
                        .target(it) // set the target to the latlng
                        .zoom(17f) // set the zoom level
                        .build()

                    // update the camera position state with the new camera position
                    cameraPositionState.position = cameraPosition
                }


                onLocationReady(place)
            }
        }
//        Log.d("PlacePrediction", "funciton $location")

//        return location
    }

    // function to get place details using the placeId
    fun getPlaceDetails(placeId: String, onComplete: (Place?) -> Unit){
        // define the place fields to be retrieved(in this case, only latlng)
        val placesClient = PlacesApiClient.getPlacesClient()
        val placeFields = listOf(Place.Field.LAT_LNG)
        // create a request to fetch place details using the placeId and placeFields
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        // fetch the place details asynchronously
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                // if the request is successful, extract the place information
                val place = response.place
                val t = place.phoneNumber
                // get latlng of the place
                val latLng = place.latLng
                // call the onComplete lambda with the latlng
                onComplete(place)
            }
            .addOnFailureListener { exception ->
                if(exception is ApiException){
                    Log.e("PlaceDetails", "Place details request failed: ${exception.message}")
                }
            }
    }

    fun searchPlaces(query: String, currentLocation: LatLng, context: Context, onSearchResults: (List<LocationModel>) -> Unit) {
        // Initialize Places api client
//        Places.initialize(context, "") // api
//        val placesClient = Places.createClient(context)
        PlacesApiClient.initialize(context)
        Log.d("PlacePrediction", "Place details request current location: $currentLocation")

        // create a new token for autocomplete sessions
        val token = AutocompleteSessionToken.newInstance()

        //define search bounds on current location, it draws a square around the current location
        // and searches for places within that square
        val bounds = RectangularBounds.newInstance(
            LatLng(currentLocation.latitude - 0.15, currentLocation.longitude - 0.15),
            LatLng(currentLocation.latitude + 0.15, currentLocation.longitude + 0.15)
        )

        // creating a request for autocomplete predictions
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .setLocationBias(bounds)
            .build()

        // list for storing search results
        val locationList = mutableListOf<LocationModel>()

        // perform the autocomplete search
        PlacesApiClient.getPlacesClient().findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                // iterate through the predictions and add them to the list
                for (prediction in response.autocompletePredictions) {
                    val placeId = prediction.placeId
                    val placeName = prediction.getPrimaryText(null).toString()
                    val placeAddress = prediction.getSecondaryText(null).toString()

                    Log.d(
                        "PlacePrediction",
                        "Place ID: $placeId, Name: $placeName, Address: $placeAddress"
                    )
                    locationList.add(LocationModel(placeId, placeName, placeAddress))
                }
            }
            // once the search is complete, call the onSearchResults lambda
            .addOnCompleteListener {
                onSearchResults(locationList)
            }
            // handle any errors that may occur
            .addOnFailureListener { exception ->
                if (exception is ApiException) {
                    Log.e("PlacePrediction", "Place prediction failed: ${exception.message}")
                }
            }
    }
}