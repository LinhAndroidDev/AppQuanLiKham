package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogUpdateAllocationBinding

class DialogUpdateAllocation : DialogFragment() {
    private var binding: DialogUpdateAllocationBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogUpdateAllocationBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
    }
}