package com.example.appkhambenh.ui.ui.user.service

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityExaminationServiceBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExaminationServiceActivity :
    BaseActivity<EmptyViewModel, ActivityExaminationServiceBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(FragmentSpecialist(), R.id.changeIdExaminationService)
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityExaminationServiceBinding.inflate(inflater)

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdExaminationService)
        if (fm != null && fm is BaseFragment<*, *>) {
            if (fm.onFragmentBack()) {
                finish()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}