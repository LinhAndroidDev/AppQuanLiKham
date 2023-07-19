package com.example.appkhambenh.ui.ui.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityLoginWithDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.department.EditDepartmentClinicActivity
import com.example.appkhambenh.ui.ui.doctor.time_working.EditTimeWorkActivity

class LoginWithDoctorActivity : BaseActivity<EmptyViewModel, ActivityLoginWithDoctorBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.llEditWorkingTime.setOnClickListener {
            val intent = Intent(this@LoginWithDoctorActivity, EditTimeWorkActivity::class.java)
            startActivity(intent)
        }

        binding.llEditListClinic.setOnClickListener {
            val intent = Intent(this@LoginWithDoctorActivity, EditDepartmentClinicActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityLoginWithDoctorBinding.inflate(inflater)
}