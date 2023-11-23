package com.example.appkhambenh.ui.ui.user.csyt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentDoctorCsytBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.csyt.adapter.DoctorCsytAdapter

class FragmentDoctorCsyt : BaseFragment<EmptyViewModel, FragmentDoctorCsytBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        val doctors = arrayListOf<Int>()
        for(i in 0 until 10){
            doctors.add(1)
        }
        val adapter = DoctorCsytAdapter(doctors)
        binding.rcvDoctorCsyt.adapter = adapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentDoctorCsytBinding.inflate(inflater)

}