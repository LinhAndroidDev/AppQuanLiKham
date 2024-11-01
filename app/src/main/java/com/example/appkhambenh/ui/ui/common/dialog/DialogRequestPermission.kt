package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.databinding.LayoutCustomDialogBinding

class DialogRequestPermission : DialogFragment() {
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

        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.notify.text = title
        binding.message.text = message
        binding.agree.setOnClickListener { yes?.invoke() }
        binding.skip.setOnClickListener { no?.invoke() }
    }
}
