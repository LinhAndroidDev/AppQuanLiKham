package com.example.appkhambenh.ui.ui.doctor.treatment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.databinding.FragmentClinicalExaminationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentClinicalExaminationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

            var clinical: ServiceOrderModel? = null
//            services?.forEach {
//                if(it.serviceId == 3) clinical = it
//            }
            edtCirculatoryDiagnosis.setText(clinical?.circulatoryDiagnosis)
            edtRespiratoryDiagnosis.setText(clinical?.respiratoryDiagnosis)
            edtGastrointestinalDiagnosis.setText(clinical?.gastrointestinalDiagnosis)
            edtUrogenitalDiagnosis.setText(clinical?.urogenitalDiagnosis)
            edtNeurologicalDiagnosis.setText(clinical?.neurologicalDiagnosis)
            edtMusculoskeletalDiagnosis.setText(clinical?.musculoskeletalDiagnosis)
            edtOtolaryngologicalDiagnosis.setText(clinical?.otolaryngologicalDiagnosis)
            edtMaxillofacialDiagnosis.setText(clinical?.maxillofacialDiagnosis)
            edtOphthalmicDiagnosis.setText(clinical?.ophthalmicDiagnosis)
            otherDiagnosis.setText(clinical?.otherDiagnosis)
            syndrome.setText(clinical?.syndrome)

            update.setOnClickListener {
                val updateInfoClinicalExaminationRequest = UpdateInfoClinicalExaminationRequest(
                    edtCirculatoryDiagnosis.text.toString(),
                    edtRespiratoryDiagnosis.text.toString(),
                    edtGastrointestinalDiagnosis.text.toString(),
                    edtUrogenitalDiagnosis.text.toString(),
                    edtNeurologicalDiagnosis.text.toString(),
                    edtMusculoskeletalDiagnosis.text.toString(),
                    edtOtolaryngologicalDiagnosis.text.toString(),
                    edtMaxillofacialDiagnosis.text.toString(),
                    edtOphthalmicDiagnosis.text.toString(),
                    otherDiagnosis.text.toString(),
                    syndrome.text.toString(),
                )
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        viewModel.updateClinicalExamination(
                            clinical?.id ?: 0,
                            updateInfoClinicalExaminationRequest,
                        )
                    }
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentClinicalExaminationBinding.inflate(inflater)
}