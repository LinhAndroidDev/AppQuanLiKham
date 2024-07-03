package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAdminDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogAddManagePatient
import com.example.appkhambenh.ui.ui.common.dialog.DialogExpiredToken
import com.example.appkhambenh.ui.ui.doctor.adapter.InfoMainPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.LineInformationPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentAdminDoctorViewModel
import com.example.appkhambenh.ui.utils.TokenManager
import com.example.appkhambenh.ui.utils.addFragmentByTag
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
                viewModel.patients.collect { patients ->
                    patients?.let {
                        initListPatient(patients)
                    }
                }
            }
        }

        handleTokenExpired()
    }

    private fun handleTokenExpired() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                TokenManager.tokenExpiredEvent.collect { isExpired ->
                    if(isExpired) {
                        val dialog = DialogExpiredToken()
                        dialog.show(parentFragmentManager, "DialogExpired")
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
    }

    private fun initListPatient(patients: ArrayList<PatientModel>) {
        val adapter = LineInformationPatientAdapter()
        adapter.items = patients
        adapter.onClickItem = {
            goToFragmentEditInfoPatient(it)
        }
        binding.rcvInfoPatient.adapter = adapter

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
                goToFragmentMedicalExaminationHistory()
            }

            dialog.onClickManageTreatment = {
                goToFragmentTreatment(patient)
            }
        }
        binding.rcvMainInfo.adapter = adapterInfo
    }

    private fun goToFragmentMedicalExaminationHistory() {
        val fragmentMedicalExaminationHistory = FragmentMedicalExaminationHistory()
        addFragmentByTag(fragmentMedicalExaminationHistory, R.id.changeIdDoctorVn, "FragmentAdminDoctor")
    }

    private fun goToFragmentTreatment(patient: PatientModel) {
        val fragmentTreatmentManagement = FragmentTreatmentManagement()
        val bundle = Bundle()
        bundle.putParcelable(OBJECT_PATIENT, patient)
        fragmentTreatmentManagement.arguments = bundle
        addFragmentByTag(fragmentTreatmentManagement, R.id.changeIdDoctorVn, "FragmentAdminDoctor")
    }

    private fun goToFragmentEditInfoPatient(patient: PatientModel) {
        val fragmentEditInfoPatient = FragmentEditInfoPatient()
        val bundle = Bundle()
        bundle.putParcelable(OBJECT_PATIENT, patient)
        fragmentEditInfoPatient.arguments = bundle
        addFragmentByTag(fragmentEditInfoPatient, R.id.changeIdDoctorVn, "FragmentAdminDoctor")
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAdminDoctorBinding.inflate(inflater)
}