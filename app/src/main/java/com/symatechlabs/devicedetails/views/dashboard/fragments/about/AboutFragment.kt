package com.symatechlabs.devicedetails.views.dashboard.fragments.about;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class AboutFragment : Fragment() {

    lateinit var aboutFragmentMvc: AboutFragmentMvc;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutFragmentMvc = AboutFragmentMvc(LayoutInflater.from(context), null);
        aboutFragmentMvc.setListerners();

        return aboutFragmentMvc.getRootView_();
    }

}