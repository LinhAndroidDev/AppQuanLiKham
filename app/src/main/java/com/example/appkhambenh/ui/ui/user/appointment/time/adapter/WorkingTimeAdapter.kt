package com.example.appkhambenh.ui.ui.user.appointment.time.adapter

import android.content.Context
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.TimeWorking

@Suppress("DEPRECATION")
class WorkingTimeAdapter(
    val context: Context,
    private val listTime: ArrayList<TimeWorking>?
) : RecyclerView.Adapter<WorkingTimeAdapter.TimeViewHolder>() {

    var onClickSelectAppointment: ((TimeWorking)->Unit)? = null

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

        if(time?.is_registered == 1){
            holder.hour.setBackgroundResource(R.color.grey)
            holder.hour.setTextColor(context.resources.getColor(R.color.black))
        }

        holder.itemView.setOnClickListener {
            if(time?.is_registered == 1) {
                Toast.makeText(context, "Lịch khám đã được đặt", Toast.LENGTH_SHORT).show()
            }else{
                onClickSelectAppointment?.invoke(time!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return listTime?.size ?: 0
    }
}