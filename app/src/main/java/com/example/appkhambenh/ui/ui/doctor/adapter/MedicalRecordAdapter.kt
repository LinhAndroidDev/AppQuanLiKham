package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemMedicalRecordBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class MedicalRecordAdapter : BaseAdapter<Int, ItemMedicalRecordBinding>() {

    override fun getItemCount(): Int = 5

    override fun getLayout(): Int = R.layout.item_medical_record

    override fun onBindViewHolder(holder: BaseViewHolder<ItemMedicalRecordBinding>, position: Int) {

    }
}