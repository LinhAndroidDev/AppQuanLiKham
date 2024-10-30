package com.example.appkhambenh.ui.ui.user.manage_appointment.adapter

import android.annotation.SuppressLint
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemExaminationBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.entity.DetailAppointment
import com.example.appkhambenh.ui.utils.DateUtils

class ExaminationAdapter : BaseAdapter<DetailAppointment, ItemExaminationBinding>() {
    var onClickItem: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_examination

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemExaminationBinding>, position: Int) {
        val doctor = items[position]
        holder.v.apply {
            tvNameDoctor.text = doctor.doctorName
            tvNameHospital.text = doctor.hospitalName
            tvNamePatient.text = doctor.patientName
            tvHour.text = doctor.time
            doctor.day?.let {
                val strDate = DateUtils.convertLongToDate(it).split("/")
                tvDay.text = strDate[0]
                tvMonthAndYear.text = "${strDate[1]}/${strDate[2]}"
            }
        }
        holder.itemView.setOnClickListener {
            onClickItem?.invoke()
        }
    }
}