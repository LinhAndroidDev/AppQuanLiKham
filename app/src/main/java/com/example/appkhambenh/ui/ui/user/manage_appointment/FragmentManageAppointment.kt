package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.databinding.FragmentManageAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ManageAppointmentAdapter

class FragmentManageAppointment : BaseFragment<ManageAppointmentViewModel, FragmentManageAppointmentBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.getListAppointment()
    }

    private fun initUi() {

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingData.visibility = View.VISIBLE
            }else{
                binding.loadingData.visibility = View.GONE
            }
        }

        viewModel.listAppointmentLiveData.observe(viewLifecycleOwner) {
            val adapterManageAppointment =
                ManageAppointmentAdapter(it, requireActivity())
            val linear = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rcvManageAppointment.layoutManager = linear
            binding.rcvManageAppointment.adapter = adapterManageAppointment
        }

        binding.backManageAppoint.setOnClickListener {
            activity?.onBackPressed()
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