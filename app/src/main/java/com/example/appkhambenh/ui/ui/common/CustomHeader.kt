package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.appkhambenh.databinding.LayoutHeaderCommonBinding
import com.example.appkhambenh.ui.base.BaseActivity

class CustomHeader : LinearLayout{
    val binding by lazy { LayoutHeaderCommonBinding.inflate(LayoutInflater.from(context)) }
    var isRevert: ((Boolean) -> Unit)? = null
    var searchItem: ((String) -> Unit)? = null

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
        binding.backHeader.setOnClickListener {
            (context as BaseActivity<*, *>).back()
        }

        binding.setting.setOnClickListener {
            isRevert?.invoke(true)
        }

        binding.searchHeader.keySearch = {
            searchItem?.invoke(it)
        }
    }

    fun setTitle(title: String){
        binding.titleHeader.text = title
    }

    fun setHint(hint: String){
        binding.searchHeader.setHint(hint)
    }

    fun clearFocusSearch(){
        binding.searchHeader.clrFocus()
    }

    fun visibleSetting(){
        binding.setting.visibility = View.VISIBLE
    }

    fun visibleSearch(){
        binding.searchHeader.visibility = View.VISIBLE
    }
}