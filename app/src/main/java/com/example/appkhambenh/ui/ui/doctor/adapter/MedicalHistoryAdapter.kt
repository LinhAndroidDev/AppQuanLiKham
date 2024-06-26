package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDetailMedicalHistoryBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class DetailMedicalHistoryAdapter : BaseAdapter<Int, ItemDetailMedicalHistoryBinding>() {
    var diagnose: (() -> Unit)? = null
    var allocation: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_detail_medical_history

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemDetailMedicalHistoryBinding>,
        position: Int
    ) {
        holder.v.apply {
            btnDiagnose.setOnClickListener { diagnose?.invoke() }
            btnAllocation.setOnClickListener { allocation?.invoke() }
            btnOutHospital.setOnClickListener {  }
        }
    }

}