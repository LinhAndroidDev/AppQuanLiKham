package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.BtnGenderBinding
import com.example.appkhambenh.ui.utils.setBgColorViewTint

class CustomBtnGender : LinearLayout {
    private val binding by lazy { BtnGenderBinding.inflate(LayoutInflater.from(context)) }

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        addView(binding.root)
    }

    fun stsSelected() {
        binding.bgGender.setBgColorViewTint(R.color.background)
        binding.icGender.setColorFilter(context.getColor(R.color.white))
        binding.nameGender.setTextColor(context.getColor(R.color.white))
    }

    fun stsUnSelected() {
        binding.bgGender.setBgColorViewTint(R.color.grey)
        binding.icGender.setColorFilter(context.getColor(R.color.grey_1))
        binding.nameGender.setTextColor(context.getColor(R.color.text_common))
    }

    fun genderFemale() {
        binding.bgGender.setBackgroundResource(R.drawable.bg_gender_famale)
        binding.icGender.setImageResource(R.drawable.ic_female)
        binding.nameGender.text = "Nữ"
    }
}