package com.example.appkhambenh.ui.ui.user.contact

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityCallWithDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.contact.adapter.DoctorOnlineAdapter
import com.example.appkhambenh.ui.ui.user.contact.adapter.PackagePromotionAdapter

class CallWithDoctorActivity : BaseActivity<EmptyViewModel, ActivityCallWithDoctorBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backHeader.setOnClickListener { onBackPressed() }

        binding.rcvForYou.adapter = PackagePromotionAdapter()
        binding.rcvDoctorOnline.adapter = DoctorOnlineAdapter()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityCallWithDoctorBinding.inflate(inflater)
}