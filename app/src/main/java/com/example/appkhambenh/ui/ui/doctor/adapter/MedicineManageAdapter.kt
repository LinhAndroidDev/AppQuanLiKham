package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDetailMedicineBinding
import com.example.appkhambenh.databinding.ItemNameMedicineBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.model.MedicineModel

class NameMedicineAdapter : BaseAdapter<MedicineModel, ItemNameMedicineBinding>() {
    override fun getLayout(): Int = R.layout.item_name_medicine

    override fun onBindViewHolder(holder: BaseViewHolder<ItemNameMedicineBinding>, position: Int) {
        val medicine = items[position]
        holder.v.apply {
            idMedicine.text = medicine.id.toString()
            nameMedicine.text = medicine.name.toString()
        }
    }

}

class DetailMedicineAdapter : BaseAdapter<MedicineModel, ItemDetailMedicineBinding>() {
    override fun getLayout(): Int = R.layout.item_detail_medicine

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemDetailMedicineBinding>,
        position: Int,
    ) {
        val medicine = items[position]
        holder.v.apply {
            inventoryNumber.text = medicine.inventoryNumber.toString()
            updateDay.text = medicine.updateDay
        }
    }

}