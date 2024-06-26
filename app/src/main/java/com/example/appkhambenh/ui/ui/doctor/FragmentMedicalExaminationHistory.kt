package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentMedicalExaminationHistoryBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogUpdateAllocation
import com.example.appkhambenh.ui.ui.common.dialog.DialogUpdateDiagnose
import com.example.appkhambenh.ui.ui.doctor.adapter.DetailMedicalHistoryAdapter

class FragmentMedicalExaminationHistory :
    BaseFragment<EmptyViewModel, FragmentMedicalExaminationHistoryBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()
        initListMedicalHistory()
    }

    private fun initListMedicalHistory() {
        val detailMedicalAdapter = DetailMedicalHistoryAdapter()
        detailMedicalAdapter.diagnose = {
            val dialogUpdateDiagnose = DialogUpdateDiagnose()
            dialogUpdateDiagnose.show(requireActivity().supportFragmentManager, "DialogUpdateDiagnose")
        }
        detailMedicalAdapter.allocation = {
            val dialogUpdateAllocation = DialogUpdateAllocation()
            dialogUpdateAllocation.show(requireActivity().supportFragmentManager, "DialogUpdateAllocation")
        }
        detailMedicalAdapter.items = arrayListOf(1, 1, 1)
        binding.rcvDetailMedical.adapter = detailMedicalAdapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentMedicalExaminationHistoryBinding.inflate(inflater)

}