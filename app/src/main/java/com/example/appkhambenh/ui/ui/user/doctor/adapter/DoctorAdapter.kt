package com.example.appkhambenh.ui.ui.user.doctor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R

class DoctorAdapter(private val doctors: ArrayList<Int>) : Adapter<DoctorAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtNameDr)
        val csyt: TextView = itemView.findViewById(R.id.csytDr)
        val specialist: TextView = itemView.findViewById(R.id.specialistDr)
        val numberVisit: TextView = itemView.findViewById(R.id.txtNumberVisitDr)
        val evaluate: TextView = itemView.findViewById(R.id.txtEvaluateDr)
        val time: TextView = itemView.findViewById(R.id.txtLatestMedicalSchedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return doctors.size
    }
}