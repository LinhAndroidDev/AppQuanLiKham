package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutCustomSelectLaguageBinding

class CustomSelectLanguage : LinearLayout{
    val binding by lazy { LayoutCustomSelectLaguageBinding.inflate(LayoutInflater.from(context)) }

    constructor(context: Context?) : super(context){ initView() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){ initView() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){ initView() }

    private fun initView(){
        addView(binding.root)

        initUi()
    }

    private fun initUi() {

    }

    fun setText(str: String){
        binding.txtLanguage.text = str
    }

    private fun visibleIcon(){
        binding.imgSelectLanguage.visibility = View.VISIBLE
    }

    private fun goneIcon(){
        binding.imgSelectLanguage.visibility = View.GONE
    }

    fun selectLanguage(){
        binding.selectLanguage.setBackgroundResource(R.color.bg_blue_light)
        binding.txtLanguage.setTypeface(null, Typeface.BOLD)
        visibleIcon()
    }

    fun unSelectLanguage(){
        binding.selectLanguage.setBackgroundResource(R.color.white)
        binding.txtLanguage.setTypeface(null, Typeface.NORMAL)
        goneIcon()
    }
}