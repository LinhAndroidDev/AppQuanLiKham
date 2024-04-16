package com.example.appkhambenh.ui.ui.user.manage_appointment.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemExaminationBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.entity.DetailAppointment

class ExaminationAdapter : BaseAdapter<DetailAppointment, ItemExaminationBinding>() {
    var onClickItem: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_examination

    override fun onBindViewHolder(holder: BaseViewHolder<ItemExaminationBinding>, position: Int) {
        val doctor = items[position]
        holder.v.apply {
            tvNameDoctor.text = doctor.doctorName
            tvNameHospital.text = doctor.hospitalName
            tvNamePatient.text = doctor.patientName
            tvHour.text = doctor.time
//            tvDay.text = DateUtils.convertLongToDate(doctor.)
        }
        holder.itemView.setOnClickListener {
            onClickItem?.invoke()
        }
    }
}