package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.TitleInfoPatientBinding

class CustomTitleInfoPatient @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { TitleInfoPatientBinding.inflate(LayoutInflater.from(context)) }

    init {
        addView(binding.root)
        val array =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTitleInfoPatient, 0, 0)
        array.apply {
            binding.title.text = getString(R.styleable.CustomTitleInfoPatient_title)
            binding.viewLoad.isVisible =
                getBoolean(R.styleable.CustomTitleInfoPatient_showViewLoad, false)
            binding.viewEnd.isVisible =
                getBoolean(R.styleable.CustomTitleInfoPatient_showViewEnd, false)
            getInt(R.styleable.CustomTitleInfoPatient_sizeView, 140).let {
                binding.root.layoutParams = when (it) {
                    50 -> {
                        LayoutParams(
                            context.resources.getDimensionPixelSize(R.dimen.dimen_50),
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }

                    100 -> {
                        LayoutParams(
                            context.resources.getDimensionPixelSize(R.dimen.dimen_100),
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }

                    160 -> {
                        LayoutParams(
                            context.resources.getDimensionPixelSize(R.dimen.dimen_160),
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }

                    else -> {
                        LayoutParams(
                            context.resources.getDimensionPixelSize(R.dimen.dimen_140),
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }

                }
            }
        }
    }
}