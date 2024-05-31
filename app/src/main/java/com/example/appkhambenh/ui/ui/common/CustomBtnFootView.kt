package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutFootViewBinding
import com.example.appkhambenh.ui.utils.setBgColorViewTint

class CustomBtnFootView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { LayoutFootViewBinding.inflate(LayoutInflater.from(context)) }
    init {
        binding.root.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomBtnFootView, 0, 0)
        array.apply {
            binding.tvComplete.text = getString(R.styleable.CustomBtnFootView_title_foot_view)
            binding.tvComplete.elevation = getFloat(R.styleable.CustomBtnFootView_eleven_foot_view, 10f)
            binding.tvComplete.setBgColorViewTint(getColor(R.styleable.CustomBtnFootView_color_foot_view, context.getColor(R.color.background)))
            getBoolean(R.styleable.CustomBtnFootView_enable_foot_view, true).let {
                if(it) enableView() else disableView()
            }
        }
    }

    fun enableView() {
        binding.footView.isEnabled = true
        binding.footView.alpha = 1f
    }

    fun disableView() {
        binding.footView.isEnabled = false
        binding.footView.alpha = 0.5f
    }
}