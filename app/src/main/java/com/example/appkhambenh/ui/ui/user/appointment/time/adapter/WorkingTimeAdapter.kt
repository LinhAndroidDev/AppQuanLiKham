package com.example.appkhambenh.ui.ui.user.appointment.time.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.TimeWorking

class WorkingTimeAdapter(
    val context: Context,
    private val listTime: ArrayList<TimeWorking>?
) : RecyclerView.Adapter<WorkingTimeAdapter.TimeViewHolder>() {

    var onClickSelectAppointment: ((String)->Unit)? = null

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TimeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_time,parent,false)
        return TimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val time: TimeWorking? = listTime?.get(position)
        holder.hour.text = time?.hour

        holder.itemView.setOnClickListener {
            onClickSelectAppointment?.invoke(time?.hour.toString())
        }
    }

    override fun getItemCount(): Int {
        return listTime?.size ?: 0
    }
}