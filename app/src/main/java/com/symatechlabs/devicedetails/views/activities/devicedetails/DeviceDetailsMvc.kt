package com.symatechlabs.devicedetails.views.activities.devicedetails

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.symatechlabs.devicedetails.common.deviceinfo.DeviceInfo
import com.symatechlabs.devicedetails.databinding.DeviceDetailsBinding

import com.symatechlabs.devicedetails.views.dashboard.Dashboard


class DeviceDetailsMvc(inflater: LayoutInflater, parent: ViewGroup?, appCompatActivity: AppCompatActivity)  :
    DeviceDetailsInterface {

    var rootView: View;
    var deviceDetailsBinding: DeviceDetailsBinding;
    var appCompatActivity: AppCompatActivity;
    var deviceInfo: DeviceInfo;
    var LOG_TAG = "DeviceDetailsMvc";

    init {
        deviceDetailsBinding =  DeviceDetailsBinding.inflate(inflater);
        rootView = deviceDetailsBinding.root;
        this.appCompatActivity = appCompatActivity;
        deviceInfo = DeviceInfo(appCompatActivity, null);
    }

    override fun getRootView_(): View {
            return rootView;
    }

    override fun getContext(): Context {
        return rootView.context;
    }

    override fun setListerners() {
        deviceInfo.deviceDetails(deviceDetailsBinding);
        deviceDetailsBinding.next.setOnClickListener {
            appCompatActivity.startActivity(Intent(appCompatActivity, Dashboard::class.java));
        }
    }

}