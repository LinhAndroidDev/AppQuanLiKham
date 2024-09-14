package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogAddManagePatientBinding
import com.example.appkhambenh.ui.ui.doctor.FragmentAdminDoctor
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl

class DialogAddManagePatient : DialogFragment() {
    private lateinit var binding: DialogAddManagePatientBinding
    var onClickManageTreatment: (() -> Unit)? = null
    var onClickHistoryTest: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddManagePatientBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val width = resources.getDimensionPixelSize(R.dimen.dimen_300)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        SharePreferenceRepositoryImpl(requireActivity()).getRollUser().let {
            if(it == 1 || it == 2) {
                binding.historyTest.isVisible = true
            }
        }

        val name = arguments?.getString(FragmentAdminDoctor.NAME_PATIENT)
        binding.tvNamePatient.text = name

        binding.manageTreatment.setOnClickListener {
            dismiss()
            onClickManageTreatment?.invoke()
        }

        binding.historyTest.setOnClickListener {
            dismiss()
            onClickHistoryTest?.invoke()
        }

        binding.close.setOnClickListener {
            dismiss()
        }
    }
}