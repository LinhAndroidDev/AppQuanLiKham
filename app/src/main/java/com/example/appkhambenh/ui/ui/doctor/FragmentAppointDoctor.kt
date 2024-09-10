package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.databinding.FragmentAppointDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.StatePatient
import com.example.appkhambenh.ui.ui.common.dialog.DialogConfirm
import com.example.appkhambenh.ui.ui.doctor.adapter.InfoAppointPatientAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentAppointDoctorViewModel
import com.example.appkhambenh.ui.utils.getDateFromCalendar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentAppointDoctor : BaseFragment<FragmentAppointDoctorViewModel, FragmentAppointDoctorBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickView()
    }

    override fun bindData() {
        super.bindData()

        lifecycleScope.launch {
            delay(1000L)
            withContext(Dispatchers.Main) {
                viewModel.getListAppointment()
                viewModel.appointments.collect {
                    initListAppoint(it)
                }
            }
        }
    }

    private fun initListAppoint(appoint: ArrayList<StatePatient>?) {
        appoint?.let {
            val infoAppointAdapter = InfoAppointPatientAdapter()
            infoAppointAdapter.onClickConfirm = {
                val dialog = DialogConfirm()
                val bundle = Bundle()
                bundle.putString(DialogConfirm.NOTIFICATION_CONFIRM, "Bạn có muốn xác nhận lịch hẹn cho bệnh nhân này không?")
                dialog.show(parentFragmentManager, "")
                dialog.arguments = bundle

                dialog.agree = {
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            viewModel.confirmAppoint(it)
                        }
                    }
                    dialog.dismiss()
                }
            }
            infoAppointAdapter.items = appoint
            binding.rcvInfoAppointPatient.adapter = infoAppointAdapter
        }
    }

    private fun onClickView() {
        binding.edtSelectDate.setOnClickListener {
            setDateWithText()
        }

        binding.edtSelectDate.doOnTextChanged { text, _, _, _ ->
            if(text?.isNotEmpty() == true) {
                binding.remove.isVisible = true
                binding.calendar.isVisible = false
            } else {
                binding.remove.isVisible = false
                binding.calendar.isVisible = true
            }
        }

        binding.remove.setOnClickListener {
            binding.edtSelectDate.setText("")
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    viewModel.getListAppointment()
                }
            }
        }
    }

    private fun setDateWithText() {
        activity?.getDateFromCalendar {
            binding.edtSelectDate.setText(it)
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    viewModel.getListAppointment(it)
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAppointDoctorBinding.inflate(inflater)
}