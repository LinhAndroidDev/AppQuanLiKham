package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentHomeDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.FunctionDoctor
import com.example.appkhambenh.ui.ui.doctor.adapter.FunctionDoctorAdapter

class FragmentHomeDoctor : BaseFragment<EmptyViewModel, FragmentHomeDoctorBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickView()
        initListFunction()
    }

    private fun onClickView() {
        binding.header.onClickMenu = {
            (activity as DoctorActivity).openNavigationDrawer()
        }
    }

    private fun initListFunction() {
        val functions = arrayListOf<FunctionDoctor>()
        functions.add(FunctionDoctor("Số bệnh nhân", R.drawable.ic_number_patient, R.color.blue, 0))
        functions.add(FunctionDoctor("Số lịch hẹn", R.drawable.ic_number_appointment, R.color.brown1, 0))
        functions.add(FunctionDoctor("Số bệnh án", R.drawable.ic_number_medical_record, R.color.green_dark, 0))
        val adapter = FunctionDoctorAdapter(requireActivity())
        adapter.items = functions
        binding.rcvFunctionDoctor.adapter = adapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentHomeDoctorBinding.inflate(inflater)
}