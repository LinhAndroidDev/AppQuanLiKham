package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.appkhambenh.databinding.FragmentEditInfoPatientBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.ui.EmptyViewModel

class FragmentEditInfoPatient : BaseFragment<EmptyViewModel, FragmentEditInfoPatientBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams

        val patient = arguments?.getParcelable<PatientModel?>(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            binding.edtName.setText(patient.fullname.toString())
            binding.edtEmail.setText(patient.email.toString())
            binding.edtPhone.setText(patient.phoneNumber.toString())
            binding.edtBirth.setText(patient.DoB.toString())
            binding.edtCccd.setText(patient.citizenId.toString())
            binding.edtSex.setUpIndexSpinner(if(patient.sex == "0") 0 else 1)
        }

        binding.edtSex.setUpListSpinner(arrayListOf("Nam", "Nữ", "Khác"))
        binding.edtSex.indexSelected = {

        }

        binding.maritalStatus.setUpListSpinner(arrayListOf("Độc thân", "Đã kết hôn"))
        binding.maritalStatus.indexSelected = {

        }

        binding.hasHealthInsurance.setUpListSpinner(arrayListOf("Có bảo hiểm xã hội", "Không có bảo hiểm xã hội"))
        binding.hasHealthInsurance.indexSelected = {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEditInfoPatientBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return false
    }
}