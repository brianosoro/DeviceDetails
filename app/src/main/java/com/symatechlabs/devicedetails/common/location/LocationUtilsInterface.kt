package com.symatechlabs.devicedetails.common.location

import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient

interface LocationUtilsInterface {
    fun setUpLocationClient(): FusedLocationProviderClient;
    fun getLastLocation(fusedLocationClient: FusedLocationProviderClient, locationName: TextView, cordinates: TextView);
    fun getLocationName(latitude: Double, longitude: Double, locationName: TextView)
}