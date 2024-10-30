package com.example.appkhambenh.ui.ui.doctor.adapter

import android.content.Context
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemListOfServiceBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel

class ListOfServiceAdapter(private val context: Context) : BaseAdapter<ServiceOrderModel, ItemListOfServiceBinding>() {
    val services = arrayListOf("Bệnh sử tiền sử", "Chẩn đoán","Khám lâm sàng, tổng quát", "Xét nghiệm máu", "Chụp siêu âm", "Chụp X-quang", "Chụp MRI", "Chụp CT", "Thuốc sử dụng")
    var onClickPay: ((Int) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_list_of_service

    override fun onBindViewHolder(holder: BaseViewHolder<ItemListOfServiceBinding>, position: Int) {
        val listOfService = items[position]
        holder.v.apply {
            idService.text = listOfService.id.toString()
            nameService.text = services[listOfService.serviceId - 1]
            if (listOfService.status == "1") {
                stateService.text = "Đã thanh toán"
                state.text = "Đã thanh toán"
                bgPay.isEnabled = false
                bgPay.setBackgroundResource(R.drawable.bg_edit_search_info_patient_unselected)
                state.setTextColor(context.getColor(R.color.grey_1))
            } else {
                stateService.text = "Chưa thanh toán"
                state.text = "Thanh toán"
                bgPay.isEnabled = true
                bgPay.setBackgroundResource(R.drawable.bg_pay)
                state.setTextColor(context.getColor(R.color.white))
            }
            bgPay.setOnClickListener {
                onClickPay?.invoke(listOfService.id)
            }
        }
    }

}