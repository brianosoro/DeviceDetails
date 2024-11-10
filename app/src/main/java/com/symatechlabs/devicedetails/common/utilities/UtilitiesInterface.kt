package com.symatechlabs.devicedetails.common.utilities

import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File


interface UtilitiesInterface {

    fun requestPermissions(fragment: Fragment);
    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean
    fun getGlobalConnectivity();
    fun checkConnectivity(): Boolean;
    fun getGlobalConnectivity(callback: (String) -> Unit);
}