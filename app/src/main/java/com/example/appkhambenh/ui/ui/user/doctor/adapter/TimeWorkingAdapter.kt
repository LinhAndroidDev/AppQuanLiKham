package com.example.appkhambenh.ui.ui.user.doctor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Time

class TimeWorkingAdapter(val context: Context, val times: ArrayList<Time>) : Adapter<TimeWorkingAdapter.ViewHolder>() {

    private var index = 0
    var onClickTime: ((Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutTimeWorking: LinearLayout = itemView.findViewById(R.id.layoutTimeWorking)
        val time: TextView = itemView.findViewById(R.id.timeDoctor)
        val day: TextView = itemView.findViewById(R.id.dayDoctor)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TimeWorkingAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_time_working, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TimeWorkingAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.time.text = times[position].time
        holder.day.text = times[position].day

        holder.itemView.setOnClickListener {
            onClickTime?.invoke(position)
            index = position
            notifyDataSetChanged()
        }

        if(index == position){
            holder.layoutTimeWorking.setBackgroundResource(R.drawable.bg_time_select)
            holder.time.setTextColor(context.getColor(R.color.white))
            holder.day.setTextColor(context.getColor(R.color.white))
        }else{
            holder.layoutTimeWorking.setBackgroundResource(R.drawable.bg_time_un_select)
            holder.time.setTextColor(context.getColor(R.color.black))
            holder.day.setTextColor(context.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = times.size
}