package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentManageAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ManageAppointmentAdapter

class FragmentManageAppointment :
    BaseFragment<ManageAppointmentViewModel, FragmentManageAppointmentBinding>() {

    private lateinit var adapterManageAppointment: ManageAppointmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

        binding.headerManageAppoint.setTitle(getString(R.string.manage_appointment))
        binding.headerManageAppoint.visibleSearch()

        val list = arrayListOf<RegisterChecking>()
        for (i in 0 until 10) {
            list.add(
                RegisterChecking(
                    "Khám tự nguyện",
                    "Khoa điều trị tạm thời",
                    "Nguyễn Hữu Linh",
                    "Thứ tư, 18 tháng 3",
                    "10:00",
                    "Tái khám sau phẫu thuật"
                )
            )
        }

        adapterManageAppointment =
            ManageAppointmentAdapter(list, requireActivity())
        binding.rcvManageAppointment.adapter = adapterManageAppointment

        adapterManageAppointment.isCancelAppoint = { registerChecking -> }

        binding.headerManageAppoint.searchItem = {
            adapterManageAppointment.filter.filter(it)
        }

        binding.headerManageAppoint.isRevert = {
            if (it) adapterManageAppointment.revertAppoint()
        }

        binding.headerManageAppoint.visibleSetting()

        binding.layoutHideKeyboard.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            binding.headerManageAppoint.clearFocusSearch()
            false
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentManageAppointmentBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }
}