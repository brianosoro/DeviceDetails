package com.symatechlabs.devicedetails.views.dashboard

import com.symatechlabs.devicedetails.views.common.BaseMvcInterface

interface DashboardInterface : BaseMvcInterface {

    fun setListerners();
    fun setBottomNavigationView();
}