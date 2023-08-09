package com.example.appkhambenh.ui.ui.user.appointment.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.DepartmentClinic

class DepartmentAdapter(
    private val listDepartment: ArrayList<DepartmentClinic>,
    val context: Context,
) : RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {
    var onSelectDepartment: ((String)->Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameDepartment: TextView = itemView.findViewById(R.id.txt_name_function)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_function_appointment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DepartmentAdapter.ViewHolder, position: Int) {
        val department: DepartmentClinic = listDepartment[position]
        holder.nameDepartment.text = department.nameDpt

        holder.itemView.setOnClickListener {
            onSelectDepartment?.invoke(department.nameDpt!!)
        }
    }

    override fun getItemCount(): Int {
        return listDepartment.size
    }
}