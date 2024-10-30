package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.CustomNavigationDoctorBinding

class CustomNavigationDoctor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { CustomNavigationDoctorBinding.inflate(LayoutInflater.from(context)) }

    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
        val array =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomNavigationDoctor, 0, 0)
        array.apply {
            binding.title.text = getString(R.styleable.CustomNavigationDoctor_title_nav_doctor)
        }
    }

    fun checkItem() {
        binding.background.setBackgroundColor(context.getColor(R.color.background))
        binding.icon.setColorFilter(context.getColor(R.color.white))
        binding.title.setTextColor(context.getColor(R.color.white))
    }

    fun unCheckItem() {
        binding.background.setBackgroundColor(context.getColor(R.color.blue_dark))
        binding.icon.setColorFilter(context.getColor(R.color.txt_hint))
        binding.title.setTextColor(context.getColor(R.color.txt_hint))
    }
}