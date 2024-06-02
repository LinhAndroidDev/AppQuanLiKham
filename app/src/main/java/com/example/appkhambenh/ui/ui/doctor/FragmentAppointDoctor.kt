package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import com.example.appkhambenh.databinding.FragmentAppointDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.AppointmentDoctor
import com.example.appkhambenh.ui.ui.doctor.adapter.DetailInfoAppointPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.InfoAppointPatientAdapter
import com.example.appkhambenh.ui.utils.getDateFromCalendar

class FragmentAppointDoctor : BaseFragment<EmptyViewModel, FragmentAppointDoctorBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()
        binding.root.postDelayed(1000L) {
            dismissLoading()
            initListAppoint()
            onClickView()
        }
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
            setDateWithText()
        }
    }

    private fun setDateWithText() {
        requireActivity().getDateFromCalendar {
            binding.edtSelectDate.setText(it)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAppointDoctorBinding.inflate(inflater)
}