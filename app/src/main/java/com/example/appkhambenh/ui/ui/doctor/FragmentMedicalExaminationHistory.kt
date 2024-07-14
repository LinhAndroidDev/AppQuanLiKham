package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.databinding.FragmentMedicalExaminationHistoryBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import com.example.appkhambenh.ui.ui.common.dialog.DialogAddMedical
import com.example.appkhambenh.ui.ui.common.dialog.DialogConfirmOutHospital
import com.example.appkhambenh.ui.ui.common.dialog.DialogUpdateAllocation
import com.example.appkhambenh.ui.ui.common.dialog.DialogUpdateDiagnose
import com.example.appkhambenh.ui.ui.doctor.adapter.DetailMedicalHistoryAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentMedicalExaminationHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentMedicalExaminationHistory :
    BaseFragment<FragmentMedicalExaminationHistoryViewModel, FragmentMedicalExaminationHistoryBinding>() {
    private var patient: PatientModel? = null
    private var medicalHistorys: ArrayList<MedicalHistoryResponse.Data>? = null
    companion object {
        const val MEDICAL_HISTORY = "MEDICAL_HISTORY"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()
        initView()
    }

    private fun initView() {
        patient = arguments?.getParcelable(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            lifecycleScope.launch {
                delay(1000L)
                withContext(Dispatchers.Main) {
                    viewModel.medicalHistoryPatient(patientId = patient!!.id)
                    viewModel.medicalHistorys.collect { medicals ->
                        medicalHistorys = medicals
                        medicals?.let {
                            initListMedicalHistory(medicals)
                        }
                    }
                }
            }
        }

        onClickView()
    }

    private fun onClickView() {
        binding.addMedical.setOnClickListener {
            val dialog =DialogAddMedical()
            dialog.show(parentFragmentManager, "DialogAddMedical")
            dialog.addMedical = {
                if(dialog.medical?.first?.isEmpty() == true && dialog.medical?.second?.isEmpty() == true) {
                    show("Bạn chưa nhập đầy đủ thông tin")
                } else {
                    if(isDuringTreatment()) {
                        show("Bệnh nhân này đang trong quá trình điều trị")
                    } else {
                        lifecycleScope.launch {
                            withContext(Dispatchers.Main) {
                                viewModel.addMedicalHistory(
                                    AddMedicalHistoryRequest(
                                        dialog.medical!!.first,
                                        dialog.medical!!.second,
                                        patient!!.id
                                    ), patientId = patient!!.id
                                )
                            }
                        }
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    private fun isDuringTreatment(): Boolean {
        var isDuringTreatment = false
        medicalHistorys?.forEach {
            if(it.doctorId == null) {
                isDuringTreatment = true
            }
        }
        return isDuringTreatment
    }

    private fun initListMedicalHistory(medicals: ArrayList<MedicalHistoryResponse.Data>) {
        val detailMedicalAdapter = DetailMedicalHistoryAdapter(patient?.fullname.toString())
        detailMedicalAdapter.diagnose = { medicalHistoryId ->
            val dialogUpdateDiagnose = DialogUpdateDiagnose()
            val bundle = Bundle()
            bundle.putParcelable(MEDICAL_HISTORY, medicalHistorys!![0])
            dialogUpdateDiagnose.show(parentFragmentManager, "DialogUpdateDiagnose")
            dialogUpdateDiagnose.arguments = bundle
            dialogUpdateDiagnose.updateDiagnose = {
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        if(checkInfoDiagnose(dialogUpdateDiagnose.updateDiagnoseMedicalHistoryRequest)) {
                            show("Bạn chưa nhập đầy đủ thông tin")
                        } else {
                            viewModel.updateDiagnoseMedicalHistory(
                                medicalHistoryId = medicalHistoryId,
                                dialogUpdateDiagnose.updateDiagnoseMedicalHistoryRequest!!,
                                patient?.id ?: 0
                            )
                            dialogUpdateDiagnose.dismiss()
                        }
                    }
                }
            }
        }
        detailMedicalAdapter.allocation = { medicalHistoryId ->
            val dialogUpdateAllocation = DialogUpdateAllocation()
            val bundle = Bundle()
            bundle.putParcelable(MEDICAL_HISTORY, medicalHistorys!![0])
            dialogUpdateAllocation.show(parentFragmentManager, "DialogUpdateAllocation")
            dialogUpdateAllocation.arguments = bundle
            dialogUpdateAllocation.errorMessage = { show(it) }
            dialogUpdateAllocation.update = {
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        viewModel.updateAllocation(
                            medicalHistoryId,
                            dialogUpdateAllocation.allocationRequest!!,
                            patient?.id ?: 0,
                        )
                    }
                }
                dialogUpdateAllocation.dismiss()
            }
        }
        detailMedicalAdapter.outHospital = { medicalHistoryId ->
            val dialogConfirmOutHospital = DialogConfirmOutHospital()
            dialogConfirmOutHospital.show(parentFragmentManager, "DialogConfirmOutHospital")
            dialogConfirmOutHospital.confirm = {
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        viewModel.hospitalDischarge(patient?.id ?: 0, medicalHistoryId)
                    }
                }
                dialogConfirmOutHospital.dismiss()
            }
        }
        detailMedicalAdapter.items = medicals
        binding.rcvDetailMedical.adapter = detailMedicalAdapter
    }

    private fun checkInfoDiagnose(updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest?): Boolean {
        return updateDiagnoseMedicalHistoryRequest?.diagnosePast?.isEmpty() == true &&
                updateDiagnoseMedicalHistoryRequest.diagnoseNow?.isEmpty() == true &&
                updateDiagnoseMedicalHistoryRequest.diagnoseMove?.isEmpty() == true &&
                updateDiagnoseMedicalHistoryRequest.emergencyDiagnose?.isEmpty() == true

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentMedicalExaminationHistoryBinding.inflate(inflater)

}