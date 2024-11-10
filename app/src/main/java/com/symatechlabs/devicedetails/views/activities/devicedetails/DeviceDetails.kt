package com.symatechlabs.devicedetails.views.activities.devicedetails


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeviceDetails: AppCompatActivity() {

    lateinit var deviceDetailsMvc: DeviceDetailsMvc;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceDetailsMvc = DeviceDetailsMvc(LayoutInflater.from(this), null,this);
        setContentView(deviceDetailsMvc.getRootView_());
        deviceDetailsMvc.setListerners();
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(homeIntent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}