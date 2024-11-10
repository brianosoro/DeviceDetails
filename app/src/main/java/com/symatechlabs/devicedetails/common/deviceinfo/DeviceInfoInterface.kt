package com.symatechlabs.devicedetails.common.deviceinfo

import com.symatechlabs.devicedetails.databinding.DeviceDetailsBinding


interface DeviceInfoInterface {

    fun getBatteryStatus(): String;
    fun getBatteryPercentage(): String
    fun getBatteryPercentage_(): Int
    fun getTotalStorage(): String;
    fun getFreeStorage(): String;
    fun getStoragePercentage(): Double;
    fun getTotalMemoryUsage(): String;
    fun getFreeMemoryUsage(): String;
    fun getMemoryPercentage(): Double;
    fun getNetworkInfo(): String;
    fun getAppVersion(): String;
    fun getDataSyncStatus(): String;
    fun getDeviceHealth(): String;
    fun getUserActivity(): String;
    fun getDeviceIdentity(): String;
    fun getWifiSignalStrength(): String
    fun getCellularSignalStrength(callback: (String) -> Unit);
    fun getNetworkType(): String;
    fun getUptime(): String;
    fun startNetworkCallback(onNetworkChange: (String) -> Unit);
    fun stopNetworkCallback();
    fun deviceDetails(deviceDetailsBinding: DeviceDetailsBinding);
    fun getDeviceId(): String

}