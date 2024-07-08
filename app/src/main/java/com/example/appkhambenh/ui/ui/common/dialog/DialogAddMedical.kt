package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogAddMedicalBinding

class DialogAddMedical : DialogFragment() {
    private var binding: DialogAddMedicalBinding? = null
    var medical: Pair<String, String>? = null
    var addMedical: (() -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddMedicalBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)

        binding?.addMedical?.setOnClickListener {
            medical = Pair(binding?.reason?.getText() ?: "", binding?.introductionPlace?.getText() ?: "")
            addMedical?.invoke()
        }
    }
}