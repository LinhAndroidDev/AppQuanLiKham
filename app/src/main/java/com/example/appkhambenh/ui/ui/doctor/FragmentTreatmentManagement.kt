package com.example.appkhambenh.ui.ui.doctor

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentTreatmentManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogConfirm
import com.example.appkhambenh.ui.ui.doctor.adapter.ListOfServiceAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.PagerTreatmentAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentTreatmentManagementViewModel
import com.example.appkhambenh.ui.utils.addFragmentByTag
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.example.appkhambenh.ui.utils.rotationView
import com.example.appkhambenh.ui.utils.updateHeight
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Create By LinhNH 2024
 */

enum class ServiceTreatmentManagement {
    LIST_OF_SERVICE,
    MEDICAL_HISTORY,
    CLINICAL_AND_GENERAL_EXAMINATION,
    BLOOD_TESTS,
    SUPERSONIC,
    X_RAY,
    MRI,
    CT,
    DIAGNOSE
}

@AndroidEntryPoint
class FragmentTreatmentManagement : BaseFragment<FragmentTreatmentManagementViewModel, FragmentTreatmentManagementBinding>() {
    private var isExpand = false
    private var isExpandInfoIntoHospital = false
    private var patient: PatientModel? = null
    private var medicalHistoryId = 0
    private var listOfServiceAdapter: ListOfServiceAdapter? = null
    private var services: ArrayList<ServiceOrderModel>? = null
    private val permissions by lazy {
        arrayOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()
        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        showLoading()
        binding.root.postDelayed(1000L) {
            dismissLoading()
            initView()
            onClickView()
        }
    }

    private fun initView() {
        patient = arguments?.getParcelable(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            binding.tvName.text = patient?.fullname
            binding.tvAddress.text = patient?.address
            binding.tvCccd.text = patient?.citizenId
            binding.tvPhone.text = patient?.phoneNumber
            binding.tvSex.text = if(patient?.sex == "0") "Nam" else "Nữ"
        }

        medicalHistoryId = arguments?.getInt(FragmentAdminDoctor.MEDICAL_HISTORY_ID)!!
        medicalHistoryId.let {
            lifecycleScope.launch {
                delay(500L)
                withContext(Dispatchers.Main) {
                    viewModel.getServiceOrder(medicalHistoryId)
                    viewModel.services.collect { serviceModels ->
                        services = serviceModels
                        serviceModels?.let {
//                            initInfoIntoHospital()
//                            initListOfService(serviceModels)
                            initPager()
                        }
                    }
                }
            }
        }
    }

    private fun initPager() {
        val vpgAdapter = PagerTreatmentAdapter(requireActivity())
        binding.pagerTreatment.adapter = vpgAdapter
        binding.pagerTreatment.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val fragment = (binding.pagerTreatment.adapter as PagerTreatmentAdapter).createFragment(position)
                binding.pagerTreatment.updateHeight(fragment)
            }
        })

        val examinations = arrayListOf("Kê dịch vụ", "Bệnh sử tiền sử", "Khám lâm sàng, tổng quát", "Xét nghiệm máu", "Siêu âm", "X-quang", "MRI", "CT", "Chẩn đoán")
        TabLayoutMediator(binding.tabExamination, binding.pagerTreatment) { tab, position ->
            tab.text = examinations[position]
        }.attach()
    }

//    private fun initInfoIntoHospital() {
//        binding.contentInfoIntoHospital.apply {
//            titleReason.title.text = "Lý do vào viện"
//            titleIntroductionPlace.title.text = "Nơi giới thiệu"
//            titleTime.title.text = "Nhập viện lúc"
//            titleDepartment.title.text = "Khoa điều trị"
//            titleIntoHospitalNumber.title.text = "Vào viện lần thứ"
//            titleRoom.title.text = "Phòng"
//            titleBed.title.text = "Giường"
//            titleDoctor.title.text = "Bác sĩ điều trị"
//            titleDiagnosisOfDestination.title.text = "Chẩn đoán nơi chuyển đến"
//            titleDiagnosisOfKKBEmergency.title.text = "Chẩn đoán KKB, Cấp cứu"
//            titleDiagnosisCurrent.title.text = "Chẩn đoán hiện tại"
//            titleDiagnosisOut.title.text = "Chẩn đoán ra viện"
//        }
//    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun onClickView() {
        binding.prescription.setOnClickListener {
            val fragment = FragmentPrescription()
            val bundle = Bundle()
            bundle.putParcelable(FragmentAdminDoctor.OBJECT_PATIENT, patient)
            fragment.arguments = bundle
            addFragmentByTag(fragment, R.id.changeIdDoctorVn, "FragmentTreatmentManagement")
        }

//        binding.contentInfomation.post {
//            binding.titleInfoPatient.setOnClickListener {
//                isExpand = !isExpand
//                if (isExpand) {
//                    binding.iconDown.rotationView(270f, 90f)
//                    binding.contentInfomation.expandView()
//                } else {
//                    binding.iconDown.rotationView(90f, 270f)
//                    binding.contentInfomation.collapseView()
//                }
//            }
//        }
//
//        binding.contentInfoIntoHospital.layout.post {
//            binding.titleInfoIntoHospital.setOnClickListener {
//                isExpandInfoIntoHospital = !isExpandInfoIntoHospital
//                if (isExpandInfoIntoHospital) {
//                    binding.iconInfoIntoHospital.rotationView(270f, 90f)
//                    binding.contentInfoIntoHospital.layout.expandView()
//                } else {
//                    binding.iconInfoIntoHospital.rotationView(90f, 270f)
//                    binding.contentInfoIntoHospital.layout.collapseView()
//                }
//            }
//        }

        binding.listMedicalRecord.setOnClickListener {
            addFragmentByTag(FragmentListMedicalRecord(), R.id.changeIdDoctorVn, "FragmentTreatmentManagement")
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTreatmentManagementBinding.inflate(inflater)

}