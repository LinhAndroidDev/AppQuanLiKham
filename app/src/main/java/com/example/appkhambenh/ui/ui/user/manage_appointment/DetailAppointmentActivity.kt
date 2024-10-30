package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityDetailAppointmentBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel

class DetailAppointmentActivity : BaseActivity<EmptyViewModel, ActivityDetailAppointmentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.headerDetailAppoint.setTitle("Chi tiết đặt lịch")
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityDetailAppointmentBinding.inflate(inflater)
}