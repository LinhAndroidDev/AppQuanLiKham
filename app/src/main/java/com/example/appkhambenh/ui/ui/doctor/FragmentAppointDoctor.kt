package com.example.appkhambenh.ui.ui.doctor

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentAppointDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.AppointmentDoctor
import com.example.appkhambenh.ui.ui.doctor.adapter.DetailInfoAppointPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.InfoAppointPatientAdapter
import com.example.appkhambenh.ui.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FragmentAppointDoctor : BaseFragment<EmptyViewModel, FragmentAppointDoctorBinding>() {
    private val calendar by lazy { Calendar.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListAppoint()
        onClickView()
    }

    private fun initListAppoint() {
        val appoint = arrayListOf<AppointmentDoctor>()
        appoint.add(AppointmentDoctor(20, "Phạm Văn Tĩnh","đau tay", "125922207", "0982746771", "12/05/2024", false, "Hà Nội"))
        appoint.add(AppointmentDoctor(21, "Nguyễn Hữu Linh","sốt ho, cảm cúm", "125922207", "0982746771", "12/05/2024", false, "Bắc Ninnh"))
        appoint.add(AppointmentDoctor(25, "Đoàn Tiến Đạt","đau bụng", "125922207", "0982746771", "12/05/2024", true, "Hải Dương"))
        appoint.add(AppointmentDoctor(26, "Dương Minh Trường","trẹo mắt cá chân", "125922207", "0982746771", "12/05/2024", true, "Thanh Hoá"))
        appoint.add(AppointmentDoctor(27, "Doãn Văn Doanh","tái khám sau điều trị gãy xương", "125922207", "0982746771", "12/05/2024", false, "Hà Nội"))
        appoint.add(AppointmentDoctor(32, "Đoàn Văn Minh","đau tay", "125922207", "0982746771", "12/05/2024", false, "Hà Tĩnh"))

        val infoAppointAdapter = InfoAppointPatientAdapter()
        infoAppointAdapter.items = appoint
        binding.rcvInfoAppointPatient.adapter = infoAppointAdapter

        val detailInfoAdapter = DetailInfoAppointPatientAdapter()
        detailInfoAdapter.items = appoint
        binding.rcvDetailInfoAppointPatient.adapter = detailInfoAdapter
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