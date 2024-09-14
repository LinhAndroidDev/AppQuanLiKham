package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemAccountBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.model.AccountModel
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.PersonalInformation

class AccountAdapter : BaseAdapter<AccountModel, ItemAccountBinding>() {
    override fun getLayout(): Int = R.layout.item_account

    override fun onBindViewHolder(holder: BaseViewHolder<ItemAccountBinding>, position: Int) {
        val account = items[position]
        holder.v.apply {
            idAccount.text = account.id.toString()
            nameAccount.text = account.role.name
            email.text = account.email
            timeCreate.text = DateUtils.convertIsoDateTimeToDate(account.createdAt, true)
            timeUpdate.text = DateUtils.convertIsoDateTimeToDate(account.updatedAt, true)
            role.text = PersonalInformation.rolls()[account.roleId - 1]
        }
    }
}