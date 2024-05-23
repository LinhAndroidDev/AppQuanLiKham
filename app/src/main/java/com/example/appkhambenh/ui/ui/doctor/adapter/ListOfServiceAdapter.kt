package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemListOfServiceBinding
import com.example.appkhambenh.ui.base.BaseAdapter


class ListOfService(
    val id: Int,
    val nameService: String,
    val state: Boolean
)

class ListOfServiceAdapter : BaseAdapter<ListOfService, ItemListOfServiceBinding>() {
    override fun getLayout(): Int = R.layout.item_list_of_service

    override fun onBindViewHolder(holder: BaseViewHolder<ItemListOfServiceBinding>, position: Int) {
        val listOfService = items[position]
        holder.v.apply {
            idService.text = listOfService.id.toString()
            nameService.text = listOfService.nameService
            stateService.text = if(listOfService.state) "Đã thanh toán" else "Chưa thanh toán"
        }
    }

}