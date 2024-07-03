package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogUpdateMedicalHistoryValueBinding

class DialogUpdateMedicalHistoryValue : DialogFragment() {
    private val binding by lazy { DialogUpdateMedicalHistoryValueBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        val height = resources.getDimensionPixelSize(R.dimen.dimen_400)
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        binding.closeDialog.setOnClickListener { dismiss() }

        binding.cancel.setOnClickListener { dismiss() }
    }
}