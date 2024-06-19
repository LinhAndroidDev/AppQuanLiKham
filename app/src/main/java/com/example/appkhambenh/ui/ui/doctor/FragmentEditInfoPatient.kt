package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentEditInfoPatientBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentEditInfoPatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentEditInfoPatient :
    BaseFragment<FragmentEditInfoPatientViewModel, FragmentEditInfoPatientBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillView()

        val patient = arguments?.getParcelable<PatientModel?>(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            binding.edtName.setText(patient.fullname.toString())
            binding.edtEmail.setText(patient.email.toString())
            binding.edtPhone.setText(patient.phoneNumber.toString())
            binding.edtBirth.setText(patient.DoB.toString())
            binding.edtCccd.setText(patient.citizenId.toString())
            binding.edtSex.setUpIndexSpinner(if (patient.sex == "0") 0 else 1)
        }

        binding.edtSex.setUpListSpinner(arrayListOf("Nam", "Nữ", "Khác"))
        binding.maritalStatus.setUpListSpinner(arrayListOf("Độc thân", "Đã kết hôn"))
        binding.hasHealthInsurance.setUpListSpinner(
            arrayListOf(
                "Có bảo hiểm xã hội",
                "Không có bảo hiểm xã hội"
            )
        )

        binding.updateInfo.setOnClickListener {
            val patientInfoModel = PatientInfoModel(
                DoB = binding.edtBirth.getText(),
                address = binding.edtAddress.getText(),
                avatar = "",
                citizenId = binding.edtCccd.getText(),
                email = binding.edtEmail.getText(),
                fullname = binding.edtName.getText(),
                healthInsurance = binding.hasHealthInsurance.getText(),
                job = binding.edtJob.getText(),
                maritalStatus = binding.maritalStatus.indexSelected.toString(),
                nationality = binding.nationality.getText(),
                phoneNumber = binding.edtPhone.getText(),
                relativeAddress = binding.relativeAddress.getText(),
                relativeName = binding.relativeName.getText(),
                relativePhone = binding.relativePhone.getText(),
                sex = binding.edtSex.indexSelected.toString(),
                type = false
            )
            if (patient != null) {
                viewModel.updateInfoPatient(patient.id, patientInfoModel)
            }
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