package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.appkhambenh.databinding.FragmentListMedicalRecordBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.MedicalRecordAdapter

class FragmentListMedicalRecord : BaseFragment<EmptyViewModel, FragmentListMedicalRecordBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams

        binding.back.setOnClickListener { activity?.onBackPressed() }
        binding.rcvMedicalRecord.adapter = MedicalRecordAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentListMedicalRecordBinding.inflate(inflater)

}