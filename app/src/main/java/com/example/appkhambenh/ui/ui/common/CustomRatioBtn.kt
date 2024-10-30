package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.example.appkhambenh.databinding.CustomRatioBtnBinding

class CustomRatioBtn : RelativeLayout {
    private val binding by lazy { CustomRatioBtnBinding.inflate(LayoutInflater.from(context)) }
    var isCheck = false

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

    fun setLabel(str: String) {
        binding.labelRto.text = str
    }

    fun setCheck() {
        binding.imgCheck.visibility = View.VISIBLE
        binding.imgUnCheck.visibility = View.GONE
        isCheck = true
    }

    fun setUnCheck() {
        binding.imgCheck.visibility = View.GONE
        binding.imgUnCheck.visibility = View.VISIBLE
        isCheck = false
    }
}