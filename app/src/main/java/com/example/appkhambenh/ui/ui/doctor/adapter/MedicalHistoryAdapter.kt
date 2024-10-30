package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDetailMedicalHistoryBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.utils.DateUtils

class DetailMedicalHistoryAdapter(private val namePatient: String) : BaseAdapter<MedicalHistoryResponse.Data, ItemDetailMedicalHistoryBinding>() {
    var diagnose: ((Int) -> Unit)? = null
    var allocation: ((Int) -> Unit)? = null
    var outHospital: ((Int) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_detail_medical_history

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemDetailMedicalHistoryBinding>,
        position: Int
    ) {
        val medical = items[position]
        holder.v.apply {
            idMedical.text = medical.id.toString()
            nameUser.text = namePatient
            reason.text = medical.reason
            department.text = medical.facultyTreatment
            room.text = medical.room
            cdOutHospital.text = medical.diagnoseNow
            if(medical.hospitalDischarge?.isNotEmpty() == true) {
                timeOutHospital.text = DateUtils.convertIsoDateTimeToDate(medical.hospitalDischarge)
            } else {
                timeOutHospital.text = "Đang điều trị"
            }
            btnDiagnose.setOnClickListener { diagnose?.invoke(medical.id) }
            btnAllocation.setOnClickListener { allocation?.invoke(medical.id) }
            btnOutHospital.setOnClickListener { outHospital?.invoke(medical.id) }
        }
    }

}