package com.example.appkhambenh.ui.ui.doctor.treatment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.databinding.FragmentListOfServiceBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.ui.common.dialog.DialogAddService
import com.example.appkhambenh.ui.ui.common.dialog.DialogConfirm
import com.example.appkhambenh.ui.ui.doctor.adapter.ListOfServiceAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentListOfServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentListOfService : BaseFragment<FragmentListOfServiceViewModel, FragmentListOfServiceBinding>() {
    private var listOfServiceAdapter: ListOfServiceAdapter? = null
    private var medicalHistoryId = 0
    private var services: ArrayList<ServiceOrderModel>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        listOfServiceAdapter = ListOfServiceAdapter(requireActivity())
        binding.rcvTreatmentManagement.adapter = listOfServiceAdapter

        onClickView()
    }

    private fun onClickView() {
        binding.addService.setOnClickListener {
            val dialogAddService = DialogAddService()
            dialogAddService.show(parentFragmentManager, "DialogAddService")
            dialogAddService.addService = {
                if(existService(dialogAddService.idService)) {
                    show("Dịch vụ này đã được sử dụng")
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            viewModel.addService(AddServiceRequest(dialogAddService.idService, medicalHistoryId))
                        }
                    }
                    dialogAddService.dismiss()
                }
            }
        }
    }

    private fun existService(idService: Int): Boolean {
        var exist = false
        services?.forEach {
            if(it.serviceId == idService) {
                exist = true
            }
        }
        return exist
    }

    fun initListOfService(serviceModels: ArrayList<ServiceOrderModel>) {
        listOfServiceAdapter?.onClickPay = {
            val dialog = DialogConfirm()
            dialog.agree = {
                viewModel.payService(it)
                dialog.dismiss()
            }
            val bundle = Bundle()
            bundle.putString(DialogConfirm.NOTIFICATION_CONFIRM, "Bạn xác nhận thanh toán dịch vụ này?")
            dialog.show(parentFragmentManager, "")
            dialog.arguments = bundle
        }
        listOfServiceAdapter?.resetList(serviceModels)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListOfServiceBinding.inflate(inflater)
}