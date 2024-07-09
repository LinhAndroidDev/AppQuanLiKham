package com.example.appkhambenh.ui.ui.doctor.treatment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentClinicalExaminationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentClinicalExaminationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentClinicalExamination :
    BaseFragment<FragmentClinicalExaminationViewModel, FragmentClinicalExaminationBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        initClinical()
    }

    private fun initClinical() {
        binding.apply {
            titleDiagnosisOfCirculatorySystem.title.text = "Chẩn đoán hệ tuần hoàn"
            titleDiagnosisOfDigestiveSystem.title.text = "Chẩn đoán hệ tiêu hoá"
            titleNervousSystemDiagnosis.title.text = "Chẩn đoán hệ thần kinh"
            titleENTDiagnosis.title.text = "Chẩn đoán tai mũi họng"
            titleDiagnosisOfRespiratorySystem.title.text = "Chẩn đoán hệ hô hấp"
            titlEurogenitalDiagnosis.title.text = "Chẩn đoán niệu sinh dục"
            titleDiagnosisOfMusculoskeletalSystem.title.text = "Chẩn đoán hệ xương khớp"
            titleDiagnosisOfMaxillofacialSystem.title.text = "Chẩn đoán hệ răng hàm mặt"
            titleHumanDiagnosis.title.text = "Chẩn đoán nhân khoa"
            titleSyndrome.title.text = "Hội chứng"
            titleOtherDiagnosis.title.text = "Chẩn đoán khác"
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentClinicalExaminationBinding.inflate(inflater)
}