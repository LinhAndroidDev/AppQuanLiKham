package com.example.appkhambenh.ui.ui.user.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSearchDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.doctor.adapter.DoctorAdapter

class FragmentSearchDoctor : BaseFragment<EmptyViewModel, FragmentSearchDoctorBinding>(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {

        binding.headerDoctor.setTitle(getString(R.string.consulting_doctor))
        binding.headerDoctor.setHint(getString(R.string.search_doctor))

        listDoctor()
    }

    private fun listDoctor() {
        val doctors = arrayListOf<Int>()
        for (i in 0..10){
            doctors.add(1)
        }
        val adapter = DoctorAdapter(doctors)
        binding.rcvDoctor.adapter = adapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSearchDoctorBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }
}