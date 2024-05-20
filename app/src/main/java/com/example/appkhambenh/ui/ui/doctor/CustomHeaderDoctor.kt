package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.HeaderDoctorBinding

class CustomHeaderDoctor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { HeaderDoctorBinding.inflate(LayoutInflater.from(context)) }
    var onClickMenu: (() -> Unit)? = null

    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
        val array =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomHeaderDoctor, 0, 0)
        array.apply {
            binding.titleView.text = getString(R.styleable.CustomHeaderDoctor_title_view_doctor)
            binding.titleNext.text = getString(R.styleable.CustomHeaderDoctor_title_next)
        }

        binding.menu.setOnClickListener {
            onClickMenu?.invoke()
        }
    }
}