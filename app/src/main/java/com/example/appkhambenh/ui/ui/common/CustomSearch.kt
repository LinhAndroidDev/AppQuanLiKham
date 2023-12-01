package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.doOnTextChanged
import com.example.appkhambenh.databinding.LayoutCustomSearchBinding

class CustomSearch : RelativeLayout {
    val binding by lazy { LayoutCustomSearchBinding.inflate(LayoutInflater.from(context)) }
    var keySearch: ((String) -> Unit)? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
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
        addView(binding.root)

        initUi()
    }

    private fun initUi() {
        disableDeleteText()

        binding.search.doOnTextChanged { text, _, _, _ ->
            keySearch?.invoke(text.toString())
            if (text?.trim()!!.isNotEmpty()) enableDeleteText() else disableDeleteText()
        }

        binding.deleteTxt.setOnClickListener { clearText() }
    }

    private fun enableDeleteText() {
        binding.deleteTxt.isEnabled = true
        binding.deleteTxt.visibility = View.VISIBLE
    }

    private fun disableDeleteText() {
        binding.deleteTxt.isEnabled = false
        binding.deleteTxt.visibility = View.GONE
    }

    fun setHint(hint: String) {
        binding.search.hint = hint
    }

    fun clearText() {
        binding.search.setText("")
    }

    fun clrFocus() {
        binding.search.clearFocus()
    }
}