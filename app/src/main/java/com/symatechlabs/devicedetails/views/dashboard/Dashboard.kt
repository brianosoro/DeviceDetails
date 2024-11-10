package com.symatechlabs.devicedetails.views.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class Dashboard: AppCompatActivity() {

    lateinit var dashboardMvc: DashboardMvc;

    companion object{
        lateinit var appCompatActivity: AppCompatActivity;
    }
    var LOG_TAG = "Dashboard_";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardMvc = DashboardMvc(LayoutInflater.from(this), null, this);
        setContentView(dashboardMvc.getRootView_())
        appCompatActivity = this;
        Log.d(LOG_TAG, "initialized");
        dashboardMvc.setBottomNavigationView();
        dashboardMvc.setListerners();
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