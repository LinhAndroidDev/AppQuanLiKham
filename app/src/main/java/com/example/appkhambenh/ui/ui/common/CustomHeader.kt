package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.ImageViewCompat
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutHeaderCommonBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.utils.ConvertUtils.dpToPx
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView


class CustomHeader : LinearLayout {
    val binding by lazy { LayoutHeaderCommonBinding.inflate(LayoutInflater.from(context)) }
    var searchItem: ((String) -> Unit)? = null
    private var isExpand = false

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

        initUi()
    }

    private fun initUi() {
        binding.backHeader.setOnClickListener {
            (context as BaseActivity<*, *>).back()
        }

        binding.searchHeader.keySearch = {
            searchItem?.invoke(it)
        }

        binding.iconSearch.setOnClickListener {
            isExpand = !isExpand
            if (isExpand) visibleSearch() else goneSearch()
        }
    }

    fun setTitle(title: String) {
        binding.titleHeader.text = title
    }

    fun setHintSearch(hint: String) {
        binding.searchHeader.setHint(hint)
    }

    fun clearFocusSearch() {
        binding.searchHeader.clrFocus()
    }

    fun visibleDelete() {
        binding.delete.visibility = View.VISIBLE
    }

    fun visibleSearch() {
        binding.searchHeader.visibility = View.VISIBLE
        binding.searchHeader.expandView()
    }

    private fun goneSearch() {
        binding.searchHeader.collapseView()
    }

    fun visibleIconSearch() {
        binding.iconSearch.visibility = View.VISIBLE
    }

    fun clearSearch() {
        binding.searchHeader.clearText()
    }

    fun setBgWhite() {
        binding.bgHeader.setBackgroundResource(R.color.white)
        ImageViewCompat.setImageTintList(
            binding.backHeader,
            ColorStateList.valueOf(context.getColor(R.color.black))
        )
    }

    fun hideBtnBack() {
        binding.backHeader.visibility = View.GONE
    }
}