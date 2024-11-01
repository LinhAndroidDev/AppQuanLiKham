package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAdminDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogAddManagePatient
import com.example.appkhambenh.ui.ui.common.dialog.DialogConfirm
import com.example.appkhambenh.ui.ui.doctor.adapter.InfoMainPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentAdminDoctorViewModel
import com.example.appkhambenh.ui.utils.addFragmentByTag
import com.example.appkhambenh.ui.utils.textNullOrEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentAdminDoctor : BaseFragment<FragmentAdminDoctorViewModel, FragmentAdminDoctorBinding>() {

    companion object {
        const val OBJECT_PATIENT = "OBJECT_PATIENT"
        const val NAME_PATIENT = "NAME_PATIENT"
        const val MEDICAL_HISTORY_ID = "MEDICAL_HISTORY_ID"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickView()
    }

    override fun bindData() {
        super.bindData()

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000L)
            withContext(Dispatchers.Main) {
                viewModel.getListPatient()
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                viewModel.patients.collect { patients ->
                    patients?.let {
                        initListPatient(patients)
                    }
                }
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                viewModel.patientModel.collect { data ->
                    Log.e("Admin Doctor", "Data")
                    val patient = data?.first
                    val id = data?.second
                    if(patient != null && id != null) {
                        goToFragmentTreatment(patient, id)
                        viewModel.patientModel.value = null
                    }
                }
            }
        }
    }

    private fun onClickView() {
        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, bundle ->
            val isUpdated = bundle.getBoolean(FragmentEditInfoPatient.INFORMATION_UPDATED)
            if(isUpdated) {
                lifecycleScope.launch(Dispatchers.IO) {
                    delay(1000L)
                    withContext(Dispatchers.Main) {
                        viewModel.getListPatient()
                        viewModel.patients.collect { patients ->
                            patients?.let {
                                initListPatient(patients)
                            }
                        }
                    }
                }
            }
        }

        binding.searchPatient.setOnClickListener {
            viewModel.getListPatient(
                fullname = textNullOrEmpty(binding.namePatient.getText()),
                email = textNullOrEmpty(binding.emailPatient.getText()),
                citizenId = textNullOrEmpty(binding.cccdPatient.getText()),
                healthInsurance = textNullOrEmpty(binding.healthInsurancePatient.getText()),
                phoneNumber = textNullOrEmpty(binding.phonePatient.getText())
            )
        }

        binding.reload.setOnClickListener {
            viewModel.getListPatient()
        }
    }

    private fun initListPatient(patients: ArrayList<PatientModel>) {
        val adapterInfo = InfoMainPatientAdapter()
        adapterInfo.items = patients
        adapterInfo.onClickItem = {
            goToFragmentEditInfoPatient(it)
        }
        adapterInfo.addManager = { patient ->
            val dialog = DialogAddManagePatient()
            val bundle = Bundle()
            bundle.putString(NAME_PATIENT, patient.fullname)
            dialog.arguments = bundle
            dialog.show(parentFragmentManager, "")
            dialog.onClickHistoryTest = {
                goToFragmentMedicalExaminationHistory(patient)
            }

            dialog.onClickManageTreatment = {
                viewModel.medicalHistoryPatient(patient.id)
            }
        }

        adapterInfo.removePatient = { patientId ->
            val dialogConfirm = DialogConfirm()
            val bundle = Bundle()
            bundle.putString(DialogConfirm.NOTIFICATION_CONFIRM, "Bạn có chắc là muốn xóa bệnh nhân này?")
            dialogConfirm.show(parentFragmentManager, "DialogConfirm")
            dialogConfirm.arguments = bundle

            dialogConfirm.agree = {
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        viewModel.deletePatient(patientId)
                    }
                }
                dialogConfirm.dismiss()
            }
        }
        binding.rcvMainInfo.adapter = adapterInfo
    }

    private fun goToFragmentMedicalExaminationHistory(patient: PatientModel) {
        val fragmentMedicalExaminationHistory = FragmentMedicalExaminationHistory()
        val bundle = Bundle()
        bundle.putParcelable(OBJECT_PATIENT, patient)
        addFragmentByTag(fragmentMedicalExaminationHistory, R.id.changeIdDoctorVn, "FragmentAdminDoctor")
        fragmentMedicalExaminationHistory.arguments = bundle
    }

    private fun goToFragmentTreatment(patient: PatientModel, id: Int) {
        val fragmentTreatmentManagement = FragmentTreatmentManagement()
        val bundle = Bundle()
        bundle.putParcelable(OBJECT_PATIENT, patient)
        bundle.putInt(MEDICAL_HISTORY_ID, id)
        addFragmentByTag(fragmentTreatmentManagement, R.id.changeIdDoctorVn, "FragmentAdminDoctor")
        fragmentTreatmentManagement.arguments = bundle
    }

    private fun goToFragmentEditInfoPatient(patient: PatientModel) {
        val fragmentEditInfoPatient = FragmentEditInfoPatient()
        val bundle = Bundle()
        bundle.putParcelable(OBJECT_PATIENT, patient)
        addFragmentByTag(fragmentEditInfoPatient, R.id.changeIdDoctorVn, "FragmentAdminDoctor")
        fragmentEditInfoPatient.arguments = bundle
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAdminDoctorBinding.inflate(inflater)
}