package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.appkhambenh.databinding.FragmentTreatmentManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.Patient
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.example.appkhambenh.ui.utils.rotationView

class FragmentTreatmentManagement : BaseFragment<EmptyViewModel, FragmentTreatmentManagementBinding>() {
    private var isExpand = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams

        initView()
        onClickView()
    }

    private fun initView() {
        val patient = arguments?.getParcelable<Patient>(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            binding.tvName.text = patient.name
            binding.tvAddress.text = patient.address
            binding.tvCccd.text = patient.cccd
            binding.tvPhone.text = patient.phone
            binding.tvSex.text = patient.sex
        }
    }

    private fun onClickView() {
        binding.contentInfomation.post {
            val height = binding.contentInfomation.height

            binding.titleInfoPatient.setOnClickListener {
                isExpand = !isExpand
                if (isExpand) {
                    binding.iconDown.rotationView(270f, 90f)
                    expandView(binding.contentInfomation, height)
                } else {
                    binding.iconDown.rotationView(90f, 270f)
                    collapseView(binding.contentInfomation)
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTreatmentManagementBinding.inflate(inflater)

}