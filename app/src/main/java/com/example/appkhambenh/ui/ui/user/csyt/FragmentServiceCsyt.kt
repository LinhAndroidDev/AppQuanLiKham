package com.example.appkhambenh.ui.ui.user.csyt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentServiceCsytBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.csyt.adapter.ServiceCsytAdapter

class FragmentServiceCsyt : BaseFragment<EmptyViewModel, FragmentServiceCsytBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        val services = arrayListOf<Int>()
        for (i in 0 until 10){
            services.add(1)
        }
        val adapter = ServiceCsytAdapter(services)
        binding.rcvServiceCsyt.adapter = adapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentServiceCsytBinding.inflate(inflater)

}