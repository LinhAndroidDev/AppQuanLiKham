package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogAddServiceBinding
import com.example.appkhambenh.ui.ui.doctor.FragmentTreatmentManagement
import com.example.appkhambenh.ui.utils.initTextComplete

class DialogAddService : DialogFragment() {
    private var binding: DialogAddServiceBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogAddServiceBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        val services: List<String?> = arguments?.getStringArrayList(FragmentTreatmentManagement.LIST_SERVICE) as List<String?>
        binding?.pullDownService?.initTextComplete(requireActivity(), services)

        binding?.closeDialog?.setOnClickListener {
            dismiss()
        }
    }
}