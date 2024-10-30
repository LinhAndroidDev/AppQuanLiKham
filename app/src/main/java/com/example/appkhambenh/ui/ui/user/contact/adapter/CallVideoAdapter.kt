package com.example.appkhambenh.ui.ui.user.contact.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDoctorOnlineBinding
import com.example.appkhambenh.databinding.ItemPackagePromotionBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class PackagePromotionAdapter : BaseAdapter<Int, ItemPackagePromotionBinding>() {

    override fun getLayout(): Int = R.layout.item_package_promotion
    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemPackagePromotionBinding>,
        position: Int,
    ) {

    }
}

class DoctorOnlineAdapter : BaseAdapter<Int, ItemDoctorOnlineBinding>() {

    override fun getLayout(): Int = R.layout.item_doctor_online
    override fun getItemCount(): Int = 7
    override fun onBindViewHolder(holder: BaseViewHolder<ItemDoctorOnlineBinding>, position: Int) {

    }
}