package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemInfoAppointPatientBinding
import com.example.appkhambenh.databinding.ItemInfoMainPatientBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.entity.StatePatient
import com.example.appkhambenh.ui.utils.DateUtils

class InfoMainPatientAdapter : BaseAdapter<PatientModel, ItemInfoMainPatientBinding>() {
    var onClickItem: ((PatientModel) -> Unit)? = null
    var addManager: ((PatientModel) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_info_main_patient

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemInfoMainPatientBinding>,
        position: Int,
    ) {
        val patient = items[position]
        holder.v.apply {
            idUser.text = patient.id.toString()
            nameUser.text = patient.fullname
            address.text = patient.address
            numberBhxh.text = patient.healthInsurance
            cccd.text = patient.citizenId
            email.text = patient.email
            phone.text = patient.phoneNumber
            sex.text = if(patient.sex == "0") "Nam" else "Nữ"
            addManage.setOnClickListener {
                addManager?.invoke(patient)
            }
        }

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(patient)
        }
    }

}

class InfoAppointPatientAdapter : BaseAdapter<StatePatient, ItemInfoAppointPatientBinding>() {
    var onClickConfirm: ((Int) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_info_appoint_patient

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemInfoAppointPatientBinding>,
        position: Int
    ) {
        val appoint = items[position]
        holder.v.apply {
            nameUser.text = appoint.patient.fullname
            reason.text = appoint.reason
            cccd.text = appoint.patient.citizenId
            phone.text = appoint.patient.phoneNumber
            time.text = DateUtils.convertIsoDateTimeToDate(appoint.time)
            state.text = if(appoint.status != "0") {
                confirmAppoint.alpha = 0.5f
                confirmAppoint.isEnabled = false
                "Đã xác nhận"
            } else {
                confirmAppoint.alpha = 1f
                confirmAppoint.isEnabled = true
                "Chưa xác nhận"
            }
            introductionPlace.text = appoint.introductionPlace

            confirmAppoint.setOnClickListener {
                onClickConfirm?.invoke(appoint.id)
            }
        }
    }

}