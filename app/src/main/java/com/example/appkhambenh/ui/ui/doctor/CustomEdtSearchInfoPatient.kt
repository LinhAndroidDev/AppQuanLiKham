package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.CustomEdtInfoPatientBinding
import com.example.appkhambenh.ui.ui.common.CustomEndIconDrawable
import com.example.appkhambenh.ui.utils.createSpinner
import com.example.appkhambenh.ui.utils.getDateFromCalendar


class CustomEdtSearchInfoPatient @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { CustomEdtInfoPatientBinding.inflate(LayoutInflater.from(context)) }
    var indexSelected: Int = 0

    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEdtSearchInfoPatient, 0, 0)
        array.apply {
            binding.title.text = getString(R.styleable.CustomEdtSearchInfoPatient_title_info)
            binding.edtInfo.hint = getString(R.styleable.CustomEdtSearchInfoPatient_hint_info)
            binding.important.isVisible = getBoolean(R.styleable.CustomEdtSearchInfoPatient_important, false)

            val isCalendar = getBoolean(R.styleable.CustomEdtSearchInfoPatient_is_calendar, false)
            if(isCalendar) setUpCalendar()

            val hasPullDown = getBoolean(R.styleable.CustomEdtSearchInfoPatient_has_pull_down, false)
            if(hasPullDown) {
                binding.edtInfo.isVisible = false
                binding.pulldown.isVisible = true
            }
        }
    }

    private fun setUpCalendar() {
        binding.edtInfo.isFocusable = false
        binding.edtInfo.isClickable = true
        binding.edtInfo.setTextColor(context.getColor(R.color.txt_hint))

        val icon = ContextCompat.getDrawable(context, R.drawable.ic_action_calendar)
        val marginEnd = resources.getDimensionPixelSize(R.dimen.dimen_10)
        val iconSize = resources.getDimensionPixelSize(R.dimen.dimen_20)
        val tintColor = ContextCompat.getColor(context, R.color.grey_1)
        val endIcon = CustomEndIconDrawable(icon, marginEnd, iconSize, tintColor)
        binding.edtInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, endIcon, null)

        binding.edtInfo.setOnClickListener {
            context.getDateFromCalendar {
                binding.edtInfo.setText(it)
            }
        }
    }

    fun setUpListSpinner(list:ArrayList<String>) {
        binding.pulldown.createSpinner(context, list) {
            indexSelected = it
        }
    }

    fun setUpIndexSpinner(index: Int) {
        binding.pulldown.setSelection(index, false)
    }

    fun setText(str: String) {
        binding.edtInfo.setText(str)
    }

    fun getText(): String = binding.edtInfo.text.toString()
}