package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.appkhambenh.databinding.LayoutHeaderCommonBinding

class CustomHeader : LinearLayout{
    lateinit var binding: LayoutHeaderCommonBinding

    constructor(context: Context?) : super(context){ initView() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){ initView() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){ initView() }

    private fun initView(){
        binding = LayoutHeaderCommonBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        initUi()
    }

    private fun initUi() {
        binding.backHeader.setOnClickListener {
            (context as AppCompatActivity).onBackPressed()
        }
    }

    fun setTitle(title: String){
        binding.titleHeader.text = title
    }

    fun setHint(hint: String){
        binding.searchHeader.setHint(hint)
    }

    fun searchItem(): String = binding.searchHeader.keySearch
}