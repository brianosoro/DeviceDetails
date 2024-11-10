package com.symatechlabs.devicedetails.common.utilities


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.symatechlabs.devicedetails.common.Constants
import com.symatechlabs.devicedetails.common.Constants.CONNECTIVITY_OFFLINE
import com.symatechlabs.devicedetails.common.Constants.CONNECTIVITY_ONLINE



@SuppressLint("NewApi")
class Utilities(context: Context) : UtilitiesInterface {

    var context: Context;
    var conMgr: ConnectivityManager;
    var netInfo: NetworkInfo? = null;

    init {
        this.context = context;
        conMgr = this.context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }



    override
    fun requestPermissions(fragment: Fragment) {

        val requestMultiplePermissionLauncher =
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { result ->
                for (r in result) {
                    Log.d(Constants.LOG_TAG, r.key + " : " + r.value.toString())
                }

            }
        requestMultiplePermissionLauncher.launch(
            arrayOf(
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
        )
    }



    override fun getGlobalConnectivity() {
        if (this.context != null && conMgr != null) {
            val builder = NetworkRequest.Builder();
            conMgr.registerNetworkCallback(builder.build(), object: ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    super.onLost(network)
                    Toast.makeText(context, Constants.CONNECTIVITY_OFFLINE, Toast.LENGTH_SHORT).show();
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Toast.makeText(context, Constants.CONNECTIVITY_ONLINE, Toast.LENGTH_SHORT).show();
                }

            })
        }
    }

    override fun getGlobalConnectivity(callback: (String) -> Unit) {
        if (this.context != null && conMgr != null) {
            val builder = NetworkRequest.Builder();
            conMgr.registerNetworkCallback(builder.build(), object: ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    super.onLost(network)
                    callback(CONNECTIVITY_OFFLINE)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    callback(CONNECTIVITY_ONLINE)
                }

            })
        }
    }

    override fun checkConnectivity(): Boolean {
        netInfo = conMgr!!.activeNetworkInfo
        return netInfo != null && netInfo!!.isConnectedOrConnecting && netInfo!!.isAvailable
    }


    override
    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }







}