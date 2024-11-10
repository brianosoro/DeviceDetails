package com.symatechlabs.devicedetails.views.dashboard.fragments.about

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.symatechlabs.devicedetails.common.utilities.Utilities
import com.symatechlabs.devicedetails.databinding.AboutAppBinding


class  AboutFragmentMvc (inflater: LayoutInflater, parent: ViewGroup?)  : AboutInterface {


    var rootView: View;
    var aboutAppBinding: AboutAppBinding;
    var utilities: Utilities;


    init {
        aboutAppBinding =  AboutAppBinding.inflate(inflater);
        rootView = aboutAppBinding.root;
        this.utilities = Utilities(getContext());
    }

    override fun setListerners() {

    }

    override fun getRootView_(): View {
            return rootView;
    }

    override fun getContext(): Context {
        return rootView.context;
    }




}