package com.symatechlabs.devicedetails.common.location

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import java.util.Locale


class LocationUtils(var fragment: Fragment) : LocationUtilsInterface {

    var fragment_: Fragment;

    init {
        this.fragment_ = fragment;
    }

    override fun setUpLocationClient() : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(fragment_.requireContext())
    }

    @SuppressLint("MissingPermission")
    override fun getLastLocation(fusedLocationClient: FusedLocationProviderClient, locationName: TextView, cordinates: TextView) {
        fusedLocationClient.lastLocation
            .addOnCompleteListener(fragment_.requireActivity(), OnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val location: Location? = task.result
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        cordinates.text = "Latitude: $latitude, Longitude: $longitude";
                        getLocationName(latitude,longitude, locationName);
                    } else {
                        cordinates.text = "Location not found";
                    }
                } else {
                    cordinates.text = "Location not found";
                }
            })
    }

    override fun getLocationName(latitude: Double, longitude: Double, locationName: TextView) {
        val geocoder = Geocoder(fragment_.requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0].getAddressLine(0)
            locationName.text = address;

        } else {
            locationName.text = "No address found";
        }
    }


}