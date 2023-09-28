package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentManageAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ManageAppointmentAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

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

        val loadData = ProgressDialog(requireContext())
        loadData.setTitle("Thông báo")
        loadData.setMessage("Please wait...")
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                loadData.show()
            }else{
                loadData.dismiss()
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