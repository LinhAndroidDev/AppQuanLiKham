package com.example.appkhambenh.ui.ui.doctor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.CustomEdtBloodTestBinding

class CustomEdtBloodTest @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { CustomEdtBloodTestBinding.inflate(LayoutInflater.from(context)) }
    var passCondition = false


    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEdtBloodTest, 0, 0)
        array.apply {
            binding.unit.text = getString(R.styleable.CustomEdtBloodTest_unit) ?: "g/L"
        }

        checkInputValue()
    }

    /**
     * Check Condition Each Edittext With Value Of Input Between Start To End
     */
    private fun checkInputValue() {
        binding.edtBlood.doOnTextChanged { text, _, _, _ ->
            if(text?.isNotEmpty() == true) {
                val value = text.toString().toInt()
                if(value in 2..10) {
                    passCondition = true
                    updateUiEdt(R.drawable.bg_edit_search_info_patient, false, "")
                } else {
                    passCondition = false
                    updateUiEdt(R.drawable.bg_warning, true, "Vui lòng nhập giá trị trong khoảng từ 2 đến 10")
                }
            } else {
                passCondition = false
                updateUiEdt(R.drawable.bg_warning, true, "Vui lòng không bỏ trống trường này")
            }
        }
    }

    private fun updateUiEdt(background: Int, visibleText: Boolean, mess: String) {
        binding.edtBlood.setBackgroundResource(background)
        binding.textWarning.isVisible = visibleText
        binding.textWarning.text = mess
    }

    fun getText() = binding.edtBlood.text.toString()

    fun setText(str: String) {
        binding.edtBlood.setText(str)
    }
}