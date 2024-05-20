package com.example.appkhambenh.ui.ui.doctor

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentAppointDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FragmentAppointDoctor : BaseFragment<EmptyViewModel, FragmentAppointDoctorBinding>() {
    private val calendar by lazy { Calendar.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickView()
    }

    private fun onClickView() {
        binding.edtSelectDate.setOnClickListener {
            showDialogSelectDate()
        }
    }

    private fun showDialogSelectDate() {
        DatePickerDialog(
            requireActivity(),
            { _, year, month, dayOfMonth ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                setDateWithText()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setDateWithText() {
        val dateFormat = SimpleDateFormat(DateUtils.DAY_OF_YEAR, Locale.getDefault())
        binding.edtSelectDate.setText(dateFormat.format(calendar.time))
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAppointDoctorBinding.inflate(inflater)
}