package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemPrescriptionMedicalBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class PrescriptionMedicalAdapter : BaseAdapter<SearchMedicine, ItemPrescriptionMedicalBinding>() {
    override fun getLayout(): Int = R.layout.item_prescription_medical

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemPrescriptionMedicalBinding>,
        position: Int,
    ) {
        holder.v.apply {
            nameMedicine.text = items[position].name
        }
    }
}