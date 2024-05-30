package com.example.appkhambenh.ui.ui.doctor.adapter

import android.os.Parcelable
import com.example.appkhambenh.R
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