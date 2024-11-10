package com.symatechlabs.devicedetails.views.activities.splashscreen


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreen: AppCompatActivity() {

    lateinit var splashScreenMvc: SplashScreenMvc;
    var handler: Handler? = null;





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenMvc = SplashScreenMvc(LayoutInflater.from(this), null,this);
        setContentView(splashScreenMvc.getRootView_());

    }


    override fun onResume() {
        super.onResume()
        handler = Handler()
        splashScreenMvc.firstTimeDialog(handler);
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(homeIntent)
            handler?.removeCallbacksAndMessages(null)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}