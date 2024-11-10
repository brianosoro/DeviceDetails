package com.symatechlabs.devicedetails.views.dashboard.fragments.home;


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings



@WithFragmentBindings
@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var homeFragmentMvc: HomeFragmentFragmentMvc;


    var LOG_TAG = "Dashboard_PinFailed_HomeFrag";



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return homeFragmentMvc.getRootView_();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentMvc = HomeFragmentFragmentMvc(
            LayoutInflater.from(context),
            null,
            this
        );

        homeFragmentMvc.checkPermissions();
        homeFragmentMvc.setListerners();


    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        homeFragmentMvc.setData();
    }


}