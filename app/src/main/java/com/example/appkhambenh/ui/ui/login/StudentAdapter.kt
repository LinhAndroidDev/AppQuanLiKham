package com.example.appkhambenh.ui.ui.login

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemStudentBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class StudentAdapter : BaseAdapter<Student, ItemStudentBinding>() {
    override fun getLayout(): Int = R.layout.item_student

    override fun onBindViewHolder(holder: BaseViewHolder<ItemStudentBinding>, position: Int) {
        val student = items[position]
        holder.v.apply {
            idStudent.text = student.id.toString()
            name.text = student.name
            age.text = student.age.toString()
            address.text = student.address
        }
    }
}