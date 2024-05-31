package com.example.appkhambenh.ui.ui.doctor.adapter

import android.os.Parcelable
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDetailInfoAppointPatientBinding
import com.example.appkhambenh.databinding.ItemInfoAppointPatientBinding
import com.example.appkhambenh.databinding.ItemInfoMainPatientBinding
import com.example.appkhambenh.databinding.ItemInformationPatientBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import kotlinx.parcelize.Parcelize

@Parcelize
class Patient(
    val id: Int,
    val name: String,
    val address: String,
    val numberBhxh: String?,
    val cccd: String?,
    val email: String,
    val phone: String,
    val sex: String,
) : Parcelable

@Parcelize
class AppointmentDoctor(
    val id: Int,
    val name: String,
    val reasons: String?,
    val cccd: String?,
    val phone: String,
    val time: String,
    val state: Boolean,
    val introducePlace: String
) : Parcelable

class LineInformationPatientAdapter : BaseAdapter<Patient, ItemInformationPatientBinding>() {

    var onClickItem: ((Patient) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_information_patient

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemInformationPatientBinding>,
        position: Int,
    ) {
        val patient = items[position]
        holder.v.apply {
            address.text = patient.address
            numberBhxh.text = patient.numberBhxh
            cccd.text = patient.cccd
            email.text = patient.email
            phone.text = patient.phone
            sex.text = patient.sex
        }

        holder.v.itemView.setOnClickListener {
            onClickItem?.invoke(patient)
        }
    }
}

class InfoMainPatientAdapter : BaseAdapter<Patient, ItemInfoMainPatientBinding>() {
    var onClickItem: ((Patient) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_info_main_patient

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemInfoMainPatientBinding>,
        position: Int,
    ) {
        val patient = items[position]
        holder.v.apply {
            idUser.text = patient.id.toString()
            nameUser.text = patient.name
        }

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(patient)
        }
    }

}

class InfoAppointPatientAdapter : BaseAdapter<AppointmentDoctor, ItemInfoAppointPatientBinding>() {
    override fun getLayout(): Int = R.layout.item_info_appoint_patient

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemInfoAppointPatientBinding>,
        position: Int
    ) {
        val appoint = items[position]
        holder.v.apply {
            idUser.text = appoint.id.toString()
            nameUser.text = appoint.name
        }
    }

}

class DetailInfoAppointPatientAdapter : BaseAdapter<AppointmentDoctor, ItemDetailInfoAppointPatientBinding>() {
    override fun getLayout(): Int = R.layout.item_detail_info_appoint_patient

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemDetailInfoAppointPatientBinding>,
        position: Int
    ) {
        val appoint = items[position]
        holder.v.apply {
            reason.text = appoint.reasons
            cccd.text = appoint.cccd
            phone.text = appoint.phone
            time.text = appoint.time
            state.text = if(appoint.state) "Đã xác nhận" else "Chưa xác nhận"
            introductionPlace.text = appoint.introducePlace
        }
    }

}