package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.CustomEdtInfoPatientBinding

class CustomEdtSearchInfoPatient @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { CustomEdtInfoPatientBinding.inflate(LayoutInflater.from(context)) }
    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEdtSearchInfoPatient, 0, 0)
        array.apply {
            binding.title.text = getString(R.styleable.CustomEdtSearchInfoPatient_title_info)
            binding.edtInfo.hint = getString(R.styleable.CustomEdtSearchInfoPatient_hint_info)
        }
    }
}