package com.example.appkhambenh.ui.ui.user.navigation.information

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutCustomTextviewInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class CustomTextViewInfo : RelativeLayout
{
    lateinit var binding: LayoutCustomTextviewInfoBinding
    var isEnableButtonUpdateInfo: ((Boolean) -> Unit)? = null
    var allTextEmpty: ((Boolean) -> Unit)? = null
    private var formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.UK)

    constructor(context: Context?) : super(context){
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        initView()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding = LayoutCustomTextviewInfoBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        disableDeleteText()

        binding.edtInfo.doOnTextChanged { text, _, _, _ ->
            if(text?.trim()!!.isNotEmpty()){
                enableDeleteText()
                isEnableButtonUpdateInfo?.invoke(true)
            }else{
                disableDeleteText()
                allTextEmpty?.invoke(true)
            }
        }

        binding.deleteTextInfo.setOnClickListener {
            clearText()
        }

        binding.viewGetBirth.setOnClickListener {
            showDialogSelectDate()
        }
    }

    private fun enableDeleteText(){
        binding.deleteTextInfo.isEnabled = true
        binding.deleteTextInfo.visibility = View.VISIBLE
    }

    private fun disableDeleteText(){
        binding.deleteTextInfo.isEnabled = false
        binding.deleteTextInfo.visibility = View.GONE
    }

    private fun clearText(){
        binding.edtInfo.setText("")
    }

    fun setTextHint(str: String?){
        binding.edtInfo.hint = str
    }

    fun getTextView(): String
    = binding.edtInfo.text.toString()

    fun inputTypePhone(){
        binding.edtInfo.inputType = InputType.TYPE_CLASS_PHONE
    }

    fun visibleViewBirth(){
        binding.viewGetBirth.visibility = View.VISIBLE
    }

    private fun showDialogSelectDate() {
        val getDate = Calendar.getInstance()
        val datePicker = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                val selectDate: Calendar = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.edtInfo.setText(formatDate.format(selectDate.time))

            }, getDate.get(Calendar.YEAR), getDate.get((Calendar.MONTH)), getDate.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }
}