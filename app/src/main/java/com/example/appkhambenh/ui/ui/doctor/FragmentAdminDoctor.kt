package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAdminDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.InfoMainPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.LineInformationPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.Patient
import com.example.appkhambenh.ui.utils.addFragmentByTag

class FragmentAdminDoctor : BaseFragment<EmptyViewModel, FragmentAdminDoctorBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickView()
        initListPatient()
    }

    private fun onClickView() {
        binding.header.onClickMenu = {
            (activity as DoctorActivity).openNavigationDrawer()
        }
    }

    private fun initListPatient() {
        val patients = arrayListOf<Patient>()
        patients.add(Patient(23, "Nguyễn Hữu Linh", "Bắc Ninh", "1259256488", "125922204", "linhnh@gmail.com", "0969601767", "nam"))
        patients.add(Patient(24, "Phan Văn Hùng", "Đông Anh", "1359458959", "134898035", "hungpv@gmail.com", "0379265729", "nam"))
        patients.add(Patient(27, "Nguyễn Thị Vân", "Bắc Ninh", "129462802", "124519462", "vannt@gmail.com", "0969601767", "nữ"))
        patients.add(Patient(30, "Nguyễn Thế Dương", "Hà Nam", "12592564883", "125922204", "duongnt@gmail.com", "0969601767", "nam"))
        patients.add(Patient(31, "Phạm Hoàng Huynh", "Thanh Hoá", "12592564883", "125922204", "huynhph@gmail.com", "0969601767", "nam"))
        val adapter = LineInformationPatientAdapter()
        adapter.items = patients
        adapter.onClickItem = {
            addFragmentByTag(FragmentTreatmentManagement(), R.id.changeIdDoctorVn, "FragmentAdminDoctor")
        }
        binding.rcvInfoPatient.adapter = adapter

        val adapterInfo = InfoMainPatientAdapter()
        adapterInfo.items = patients
        adapterInfo.onClickItem = {
            addFragmentByTag(FragmentTreatmentManagement(), R.id.changeIdDoctorVn, "FragmentAdminDoctor")
        }
        binding.rcvMainInfo.adapter = adapterInfo
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAdminDoctorBinding.inflate(inflater)
}