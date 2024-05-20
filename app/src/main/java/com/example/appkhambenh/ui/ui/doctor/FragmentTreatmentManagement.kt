package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.example.appkhambenh.databinding.FragmentTreatmentManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel

class FragmentTreatmentManagement : BaseFragment<EmptyViewModel, FragmentTreatmentManagementBinding>() {
    private var isExpand = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams

        onClickView()
    }

    private fun onClickView() {
        binding.header.onClickMenu = {
            (activity as DoctorActivity).openNavigationDrawer()
        }

        binding.titleInfoPatient.setOnClickListener {
            isExpand = !isExpand
            binding.iconDown.rotation = if(isExpand) 90f else 270f
            binding.contentInfomation.isVisible = isExpand
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTreatmentManagementBinding.inflate(inflater)

}