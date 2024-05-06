package com.example.appkhambenh.ui.ui.common

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutItemAndTextBinding
import java.util.Locale

class CustomIconAndText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr){
    private val binding = LayoutItemAndTextBinding.inflate(LayoutInflater.from(context))
    init {
        addView(binding.root)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.IconAndText, 0, 0)
        array.apply {
            getString(R.styleable.IconAndText_title_view)?.let { binding.title.text = it }
            getDrawable(R.styleable.IconAndText_icon_view)?.let { binding.icon.setImageDrawable(it) }
            getColor(R.styleable.IconAndText_icon_view_color, context.getColor(R.color.background)).let {
                binding.icon.setColorFilter(it)
            }
            getBoolean(R.styleable.IconAndText_title_text_bold, false).let {  bold ->
                binding.title.typeface = if(bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            }
            getColor(R.styleable.IconAndText_title_text_color, context.getColor(R.color.txt_hint)).let {
                binding.title.setTextColor(it)
            }
//            getBoolean(R.styleable.IconAndText_title_text_all_caps, false).let {
//                binding.title.text = binding.title.text.toString().uppercase(Locale.ROOT)
//            }
        }
    }
}