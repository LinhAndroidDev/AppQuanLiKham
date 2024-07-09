package com.example.appkhambenh.ui.ui.doctor.treatment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentBloodTestBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentBloodTestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBloodTest : BaseFragment<FragmentBloodTestViewModel, FragmentBloodTestBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentBloodTestBinding.inflate(inflater)
}