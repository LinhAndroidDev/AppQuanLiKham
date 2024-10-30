package com.example.appkhambenh.ui.ui.user.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentServiceBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.service.adapter.ExaminationServiceAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class FragmentService : BaseFragment<EmptyViewModel, FragmentServiceBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.searchHeader.setHint("Tra cứu dịch vụ")
        binding.searchHeader.setBgGreySearch()
        binding.backHeader.setOnClickListener { this.back() }

        val adapter = ExaminationServiceAdapter()
        binding.rcvService.adapter = adapter
    }

    override fun onFragmentBack(): Boolean {
        return false
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentServiceBinding.inflate(inflater)
}