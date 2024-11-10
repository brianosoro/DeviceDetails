package com.symatechlabs.devicedetails.views.activities.splashscreen

import android.os.Handler
import com.symatechlabs.devicedetails.views.common.BaseMvcInterface

interface SplashScreenInterface : BaseMvcInterface {

    fun setListerners();
    fun firstTimeDialog(handler: Handler?);
}