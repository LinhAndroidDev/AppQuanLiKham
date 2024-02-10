package com.example.appkhambenh.ui.ui.common

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.databinding.LayoutCustomDialogBinding

class DialogCustom : DialogFragment() {
    var yes: (() -> Unit)? = null
    var no: (() -> Unit)? = null
    var title = ""
    var message = SpannableString("")
    private lateinit var binding: LayoutCustomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutCustomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)
        binding.notify.text = title
        binding.message.text = message
        binding.agree.setOnClickListener { yes?.invoke() }
        binding.skip.setOnClickListener { no?.invoke() }
    }
}
