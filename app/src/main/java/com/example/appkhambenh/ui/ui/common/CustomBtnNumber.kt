package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutBtnNumberBinding

class CustomBtnNumber @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { LayoutBtnNumberBinding.inflate(LayoutInflater.from(context)) }

    init {
        binding.root.layoutParams = LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.dimen_45),
            context.resources.getDimensionPixelSize(R.dimen.dimen_45)
        )
        addView(binding.root)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomBtnNumber, 0, 0).apply {
            getInt(R.styleable.CustomBtnNumber_icon_btn, 0).let {
                if (it == 1) setIconPlus()
            }
            getBoolean(R.styleable.CustomBtnNumber_icon_enable, true).let { enable ->
                if (enable) enableBtn() else disableBtn()
            }
        }.recycle()
    }

    private fun setIconPlus() {
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