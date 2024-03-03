package com.example.appkhambenh.ui.ui.user.service

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityExaminationServiceBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.service.adapter.ExaminationServiceAdapter

class ExaminationServiceActivity :
    BaseActivity<EmptyViewModel, ActivityExaminationServiceBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.searchHeader.setHint("Tra cứu dịch vụ")
        binding.searchHeader.setBgGreySearch()
        binding.backHeader.setOnClickListener { this.back() }

        val adapter = ExaminationServiceAdapter()
        adapter.services = arrayListOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
        binding.rcvService.adapter = adapter
    }


    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityExaminationServiceBinding.inflate(inflater)
}