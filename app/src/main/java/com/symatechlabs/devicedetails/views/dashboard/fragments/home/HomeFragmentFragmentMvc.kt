package com.symatechlabs.devicedetails.views.dashboard.fragments.home

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment


import com.symatechlabs.devicedetails.common.Constants.CONNECTIVITY_OFFLINE
import com.symatechlabs.devicedetails.common.deviceinfo.DeviceInfo
import com.symatechlabs.devicedetails.common.location.LocationUtils
import com.symatechlabs.devicedetails.common.utilities.Utilities
import com.symatechlabs.devicedetails.databinding.HomeFragmentBinding


class HomeFragmentFragmentMvc(
    inflater: LayoutInflater, parent: ViewGroup?, fragment: Fragment
) :
    HomeFragmentInterface {


    var rootView: View;
    var homeFragmentBinding: HomeFragmentBinding;
    var deviceInfo: DeviceInfo;
    var utilities: Utilities;
    var fragment: Fragment;
    var locationUtils: LocationUtils;

    var LOG_TAG = "HomeFragmentFragmentMvc";

    init {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater);
        rootView = homeFragmentBinding.root;
        utilities = Utilities(getContext());
        this.deviceInfo = DeviceInfo(fragment.requireContext(), fragment);
        this.fragment = fragment;
        this.locationUtils = LocationUtils(fragment);
    }

    override
    fun setListerners() {
        try {
            deviceInfo.startNetworkCallback { networkTpe ->
                homeFragmentBinding.contentMain.tvNetworkType.setText(networkTpe)
                if (networkTpe.equals("Wi-Fi", false)){
                    Log.d(LOG_TAG, "getWifiSignalStrength: "+ deviceInfo.getWifiSignalStrength());
                    homeFragmentBinding.contentMain.tvNetworkStrength.setText(deviceInfo.getWifiSignalStrength())
                }
            }

            deviceInfo.getCellularSignalStrength {
                Log.d(LOG_TAG, "getCellularSignalStrength: "+ it);
                //    homeFragmentBinding.contentMain.tvNetworkStrength.setText(it)
            }

            utilities.getGlobalConnectivity {
                if(it.equals(CONNECTIVITY_OFFLINE)){
                    fragment.requireActivity().runOnUiThread {
                        homeFragmentBinding.contentMain.tvNetworkType.text = it
                        homeFragmentBinding.contentMain.tvNetworkStrength.text = it
                    }
                }
            }

        }catch (e: Exception){

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun setData() {
        try {
            homeFragmentBinding.contentMain.tvBatteryPercentage.setText(deviceInfo.getBatteryPercentage());
            homeFragmentBinding.contentMain.batteryProgressBar.setProgress(deviceInfo.getBatteryPercentage_(),true);
            homeFragmentBinding.contentMain.tvBatteryHealth.setText(deviceInfo.getBatteryStatus());
            homeFragmentBinding.contentMain.tvStorageCapacity.setText(deviceInfo.getTotalStorage());
            homeFragmentBinding.contentMain.tvStorageAvailable.setText(deviceInfo.getFreeStorage());
            homeFragmentBinding.contentMain.storageProgressBar.setProgress(deviceInfo.getStoragePercentage().toInt(), true);
            homeFragmentBinding.contentMain.tvNetworkType.setText(deviceInfo.getNetworkType());

            homeFragmentBinding.contentMain.tvImei.setText(deviceInfo.getUptime());
            homeFragmentBinding.contentMain.tvAndroidVersion.setText(deviceInfo.getDeviceIdentity());
            homeFragmentBinding.contentMain.tvMemoryAvailable.setText(deviceInfo.getFreeMemoryUsage());
            homeFragmentBinding.contentMain.tvMemoryCapacity.setText(deviceInfo.getTotalMemoryUsage());
            homeFragmentBinding.contentMain.memoryProgressBar.setProgress(deviceInfo.getMemoryPercentage().toInt(), true);

            locationUtils.getLastLocation(locationUtils.setUpLocationClient(), homeFragmentBinding.contentMain.tvLocationName,
                homeFragmentBinding.contentMain.tvLocationCordinates);
        }catch (e: Exception){

        }
    }






    override fun checkPermissions() {
        if (!utilities.hasPermissions(
                getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.LOCATION_HARDWARE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MODIFY_PHONE_STATE,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE
            )
        ) {
            utilities.requestPermissions(fragment);
        }
    }


    override fun getRootView_(): View {
        return rootView;
    }

    override fun getContext(): Context {
        return rootView.context;
    }


}