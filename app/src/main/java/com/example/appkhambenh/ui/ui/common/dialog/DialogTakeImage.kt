package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.databinding.DialogTakeImageBinding

class DialogTakeImage : DialogFragment() {
    private lateinit var binding: DialogTakeImageBinding
    var onClickCamera: (() -> Unit)? = null
    var onClickGallery: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTakeImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)

        binding.camera.setOnClickListener {
            onClickCamera?.invoke()
            dismiss()
        }

        binding.gallery.setOnClickListener {
            onClickGallery?.invoke()
            dismiss()
        }
    }
}