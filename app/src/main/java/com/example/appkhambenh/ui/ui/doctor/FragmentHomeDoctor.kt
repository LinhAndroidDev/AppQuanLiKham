package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentHomeDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.model.Quantity
import com.example.appkhambenh.ui.ui.doctor.adapter.FunctionDoctor
import com.example.appkhambenh.ui.ui.doctor.adapter.FunctionDoctorAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentHomeDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentHomeDoctor : BaseFragment<FragmentHomeDoctorViewModel, FragmentHomeDoctorBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()
    }

    override fun bindData() {
        super.bindData()

        lifecycleScope.launch {
            delay(1000L)
            withContext(Dispatchers.Main) {
                viewModel.getQuantity()
                viewModel.quantity.collect {
                    initListFunction(it)
                }
            }
        }
    }

    private fun initListFunction(quantity: Quantity) {
        val functions = arrayListOf<FunctionDoctor>()
        functions.add(FunctionDoctor("Số bệnh nhân", R.drawable.ic_number_patient, R.color.blue, quantity.patient))
        functions.add(FunctionDoctor("Số lịch hẹn", R.drawable.ic_number_appointment, R.color.brown1, quantity.appoint))
        sharePrefer.getRollUser().let {
            if(it == 1) {
                functions.add(FunctionDoctor("Số tài khoản", R.drawable.ic_person, R.color.green, quantity.account))
            }

            if(it == 1 || it == 2) {
                functions.add(FunctionDoctor("Số bệnh án", R.drawable.ic_number_medical_record, R.color.green_dark, quantity.medical))
            }
        }
        val adapter = FunctionDoctorAdapter(requireActivity())
        adapter.items = functions
        binding.rcvFunctionDoctor.adapter = adapter
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentHomeDoctorBinding.inflate(inflater)
}