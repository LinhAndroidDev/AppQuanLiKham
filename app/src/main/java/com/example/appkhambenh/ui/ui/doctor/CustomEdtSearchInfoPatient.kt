package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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

            binding.edtInfo.isFocusable = getBoolean(R.styleable.CustomEdtSearchInfoPatient_enable_edt, true)
            binding.edtInfo.isClickable = getBoolean(R.styleable.CustomEdtSearchInfoPatient_enable_edt, true)

            binding.edtInfo.inputType = when(getInteger(R.styleable.CustomEdtSearchInfoPatient_input_type, 0)) {
                1 -> InputType.TYPE_NUMBER_FLAG_DECIMAL
                2 -> InputType.TYPE_CLASS_NUMBER
                else -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
        }
    }

    /**
     * Check Condition Each Edittext With Value Of Input Between Start To End
     */
    fun checkInputValue(start: Int, end: Int): Boolean {
        var passCondition = true
        binding.edtInfo.doOnTextChanged { text, _, _, _ ->
            if(text?.isNotEmpty() == true) {
                val value = text.toString().toInt()
                if(value in start..end) {
                    passCondition = true
                    updateUiEdt(R.drawable.bg_edit_search_info_patient, false, "")
                } else {
                    passCondition = false
                    updateUiEdt(R.drawable.bg_warning, true, "Vui lòng nhập giá trị trong khoảng từ $start đến $end")
                }
            } else {
                passCondition = false
                updateUiEdt(R.drawable.bg_warning, true, "Vui lòng không bỏ trống trường này")
            }
        }
        return passCondition
    }

    private fun updateUiEdt(background: Int, visibleText: Boolean, mess: String) {
        binding.edtInfo.setBackgroundResource(background)
        binding.textWarning.isVisible = visibleText
        binding.textWarning.text = mess
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