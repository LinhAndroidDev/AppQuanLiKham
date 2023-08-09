package com.example.appkhambenh.ui.ui.user.appointment.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Doctor

class DoctorAdapter(
    private val listDoctor: ArrayList<Doctor>,
    val context: Context
) : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    var selectDoctor: ((String)->Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameDoctor: TextView = itemView.findViewById(R.id.txt_name_function)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_function_appointment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorAdapter.ViewHolder, position: Int) {
        val doctor: Doctor = listDoctor[position]
        holder.nameDoctor.text = doctor.name

        holder.itemView.setOnClickListener {
            selectDoctor?.invoke(doctor.name!!)
        }
    }

    override fun getItemCount(): Int {
        return listDoctor.size
    }
}