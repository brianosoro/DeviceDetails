package com.symatechlabs.devicedetails.views.dashboard.fragments.home

import com.symatechlabs.devicedetails.views.common.BaseMvcInterface

interface HomeFragmentInterface : BaseMvcInterface {
     fun setListerners();
     fun setData();


     fun  checkPermissions();
}