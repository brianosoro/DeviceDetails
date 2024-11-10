package com.symatechlabs.devicedetails


import android.app.Application

import com.symatechlabs.devicedetails.common.utilities.Utilities
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {

    lateinit var utilities: Utilities;

    override
    fun onCreate() {
        super.onCreate()
        try{
            utilities = Utilities(this);
            utilities.getGlobalConnectivity();
        }catch (e: Exception){

        }

    }
}