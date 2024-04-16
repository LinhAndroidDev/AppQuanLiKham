package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutBtnNumberBinding

class CustomBtnNumber : LinearLayout {
    private val binding by lazy { LayoutBtnNumberBinding.inflate(LayoutInflater.from(context)) }

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        binding.root.layoutParams = LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.dimen_45),
            context.resources.getDimensionPixelSize(R.dimen.dimen_45)
        )
        addView(binding.root)
    }

    fun setIconPlus() {
        binding.iconBtn.setImageResource(R.drawable.ic_plus)
    }

    fun disableBtn() {
        isEnabled = false
        setBackgroundResource(R.drawable.boder_grey_light_corner_5)
        binding.iconBtn.setColorFilter(context.getColor(R.color.grey))
    }

    fun enableBtn() {
        isEnabled = true
        setBackgroundResource(R.drawable.boder_blue_corner_5)
        binding.iconBtn.setColorFilter(context.getColor(R.color.text))
    }
}