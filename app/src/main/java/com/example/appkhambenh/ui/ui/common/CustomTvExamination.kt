package com.example.appkhambenh.ui.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.example.appkhambenh.R

@SuppressLint("AppCompatCustomView")
class CustomTvExamination : TextView {
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
        setFontTv()
        statusUnChecked()
    }

    private fun setFontTv() {
        this.textSize = context.resources.getDimension(R.dimen.dimen_sp9)
        this.setTextColor(context.getColor(R.color.text_common))
    }

    fun statusUnChecked() {
        this.setBackgroundResource(R.drawable.un_select_tv_examination)
        this.typeface = Typeface.DEFAULT
    }

    fun statusChecked() {
        this.setBackgroundResource(R.drawable.select_tv_examination)
        this.typeface = Typeface.DEFAULT_BOLD
    }

    fun statusCheckedCommunity() {
        this.setBackgroundResource(R.drawable.select_tv_community)
        this.typeface = Typeface.DEFAULT_BOLD
    }

    fun statusUnCheckedCommunity() {
        this.setBackgroundResource(R.drawable.un_select_tv_community)
        this.typeface = Typeface.DEFAULT
    }
}