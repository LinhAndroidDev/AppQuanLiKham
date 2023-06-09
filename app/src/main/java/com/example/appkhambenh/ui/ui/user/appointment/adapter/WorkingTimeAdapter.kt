package com.example.appkhambenh.ui.ui.user.appointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.model.WorkingDate

class WorkingTimeAdapter(
    context: Context,
    private val listTime: ArrayList<Time>
) : RecyclerView.Adapter<WorkingTimeAdapter.TimeViewHolder>() {

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WorkingTimeAdapter.TimeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_time,parent,false)
        return TimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkingTimeAdapter.TimeViewHolder, position: Int) {
        val time: Time = listTime[position]
        holder.hour.text = time.hour
    }

    override fun getItemCount(): Int {
        return listTime.size
    }
}