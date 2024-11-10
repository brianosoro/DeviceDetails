package com.symatechlabs.devicedetails.common.deviceinfo

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.symatechlabs.devicedetails.databinding.DeviceDetailsBinding

@SuppressLint("NewApi")
class DeviceInfo(val context: Context, val fragment: Fragment?) : DeviceInfoInterface {

    var signalStrength: Int = 0;
    var signalStrengthPercentage: Int = 0;
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun getBatteryStatus(): String {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            context.registerReceiver(null, it)
        }
        val deviceHealth = batteryStatus?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
        val healthStatus = when (deviceHealth) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheating"
            else -> "Unknown"
        }
        return "Health: $healthStatus"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getNetworkType(): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "No connection";
        val netCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return "No connection";
        return when {
            netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobile"
            netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
            else -> "Other"
        }
    }

    override fun getBatteryPercentage(): String {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            context.registerReceiver(null, it)
        }
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        return "$level%"
    }


    override fun getTotalStorage(): String {
        val storage = Environment.getExternalStorageDirectory()
        val totalStorage = storage.totalSpace / (1024 * 1024 * 1024)
        return "${totalStorage} GB Total"
    }

    override fun getFreeStorage(): String {
        val storage = Environment.getExternalStorageDirectory()
        val freeStorage = storage.freeSpace / (1024 * 1024 * 1024)
        return "${freeStorage} GB Available"
    }

    override fun getStoragePercentage(): Double {
        val storage = Environment.getExternalStorageDirectory()
        val totalStorage = storage.totalSpace.toDouble() / (1024 * 1024 * 1024)
        val freeStorage = storage.freeSpace.toDouble() / (1024 * 1024 * 1024)
        return freeStorage / totalStorage * 100;
    }

    override fun getTotalMemoryUsage(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val totalMem = memoryInfo.totalMem / (1024 * 1024 * 1024)
        return "$totalMem GB"
    }

    override fun getBatteryPercentage_(): Int {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            context.registerReceiver(null, it)
        }
        return batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1;
    }


    override fun getFreeMemoryUsage(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val usedMem = (memoryInfo.totalMem - memoryInfo.availMem) / (1024 * 1024 * 1024)
        val totalMem = memoryInfo.totalMem / (1024 * 1024 * 1024)

        return "${totalMem - usedMem} GB"
    }

    override fun getMemoryPercentage(): Double {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val totalMem = memoryInfo.totalMem.toDouble()
        val availMem = memoryInfo.availMem.toDouble()
        val usedMem = totalMem - availMem
        return if (totalMem > 0) {
            (usedMem / totalMem) * 100
        } else {
            0.0
        }
    }

    override fun getNetworkInfo(): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return when {
            networkInfo?.isConnected == true && networkInfo.type == ConnectivityManager.TYPE_MOBILE -> "Connected to Cellular Mobile Data network"
            networkInfo?.isConnected == true && networkInfo.type == ConnectivityManager.TYPE_WIFI -> "Connected to Wi-Fi network"
            else -> "Offline"
        }
    }

    override fun getAppVersion(): String {
        val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        return "App Version: $version"
    }

    override fun getDataSyncStatus(): String {
        val lastSyncTime = "2024-11-04T12:00:00Z" // Example value, replace with actual sync time
        return "Last Sync: $lastSyncTime"
    }

    override fun getDeviceHealth(): String {
        val uptime = System.currentTimeMillis() - SystemClock.elapsedRealtime()
        return "Uptime: ${uptime / 1000 / 60} minutes"
    }

    override fun getUserActivity(): String {
        return "User logged in at 10:00 AM, last logout at 6:00 PM"
    }

    override fun getDeviceIdentity(): String {
        val buildNumber = Build.DISPLAY
        val androidRelease = Build.VERSION.RELEASE
        return "Android Version: $androidRelease, Build: $buildNumber"
    }

    override fun getWifiSignalStrength(): String {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "Location permission not granted"
        }

        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo

        if (wifiManager.isWifiEnabled) {
            wifiInfo?.let {
                if (it.networkId != -1) {
                    val rssi = it.rssi
                    val level = WifiManager.calculateSignalLevel(rssi, 5)
                    val percentage = (level * 100) / 4
                    return "$percentage%"
                } else {
                    return "Not connected to Wi-Fi"
                }
            }
        } else {
            return "Wi-Fi disabled"
        }
        return "N/A"
    }

    override fun getCellularSignalStrength(callback: (String) -> Unit) {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        telephonyManager.listen(object : PhoneStateListener() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                super.onSignalStrengthsChanged(signalStrength)
                // Update the signal strength
                this@DeviceInfo.signalStrength = signalStrength.level
                signalStrengthPercentage = signalStrength.level * 25
                fragment?.requireActivity()?.runOnUiThread {callback("Cellular Signal Strength: $signalStrengthPercentage")}
            }
        }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
    }



    override fun getUptime(): String {
        val uptime = SystemClock.elapsedRealtime()
        val days = uptime / (1000 * 60 * 60 * 24)
        val hours = (uptime / (1000 * 60 * 60)) % 24
        val minutes = (uptime / (1000 * 60)) % 60
        return String.format("Uptime: %d Days, %02d Hours, %02d Minutes", days, hours, minutes)
    }


    override fun startNetworkCallback(onNetworkChange: (String) -> Unit) {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            @SuppressLint("NewApi")
            override fun onAvailable(network: Network) {
                fragment?.requireActivity()?.runOnUiThread {
                    onNetworkChange(getNetworkType());
                }

            }

            override fun onLost(network: Network) {
                fragment?.requireActivity()?.runOnUiThread {
                    onNetworkChange("No connection")
                }
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback!!)
    }

    override fun stopNetworkCallback() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
            networkCallback = null
        }
    }

    override fun deviceDetails(deviceDetailsBinding: DeviceDetailsBinding) {
        deviceDetailsBinding.deviceModel.setText(Build.MODEL);
        deviceDetailsBinding.deviceID.setText(Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID));
        deviceDetailsBinding.deviceOSVersion.setText(Build.VERSION.RELEASE);
        deviceDetailsBinding.deviceName.setText(Build.MANUFACTURER + " " + Build.MODEL);
    }

    override fun getDeviceId(): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.deviceId
        } else {
            telephonyManager.deviceId ?: "UNKNOWN_DEVICE_ID"
        }
    }


}