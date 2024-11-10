package com.symatechlabs.devicedetails.views.activities.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.symatechlabs.devicedetails.common.utilities.Utilities
import com.symatechlabs.devicedetails.databinding.SplashScreenBinding
import com.symatechlabs.devicedetails.views.activities.devicedetails.DeviceDetails


class SplashScreenMvc(inflater: LayoutInflater, parent: ViewGroup?, appCompatActivity: AppCompatActivity)  :
    SplashScreenInterface {

    var rootView: View;
    var splashScreenBinding: SplashScreenBinding;
    var appCompatActivity: AppCompatActivity;
    var utilities: Utilities;
    var LOG_TAG = "SplashScreenMvc";

    init {
        splashScreenBinding =  SplashScreenBinding.inflate(inflater);
        rootView = splashScreenBinding.root;
        this.appCompatActivity = appCompatActivity;
        utilities = Utilities(getContext());
    }

    override fun getRootView_(): View {
            return rootView;
    }

    override fun getContext(): Context {
        return rootView.context;
    }

    override fun setListerners() {

    }

    override fun firstTimeDialog(handler: Handler?) {
        handler!!.postDelayed(Runnable {
            appCompatActivity.startActivity(Intent(appCompatActivity, DeviceDetails::class.java));
        }, 1500)
    }

}