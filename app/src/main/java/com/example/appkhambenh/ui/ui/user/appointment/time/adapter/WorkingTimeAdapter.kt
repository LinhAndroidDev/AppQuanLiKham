package com.example.appkhambenh.ui.ui.user.appointment.time.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
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
            .inflate(R.layout.item_time,parent,false)
        return TimeViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val time: TimeWorking? = listTime?.get(position)
        holder.hour.text = time?.hour

        if(time?.is_registered == 1){
            holder.hour.setBackgroundResource(R.color.grey)
            holder.hour.setTextColor(context.resources.getColor(R.color.black))
        }

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when(motionEvent?.actionMasked){
                MotionEvent.ACTION_DOWN->{
                    holder.itemView.alpha = 0.5f
                }
                MotionEvent.ACTION_UP ->{
                    holder.itemView.alpha = 1f
                    if(time?.is_registered == 1) {
                        Toast.makeText(context, "Lịch khám đã được đặt", Toast.LENGTH_SHORT).show()
                    }else{
                        onClickSelectAppointment?.invoke(time!!)
                    }
                }
                MotionEvent.ACTION_CANCEL->{
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return listTime?.size ?: 0
    }
}