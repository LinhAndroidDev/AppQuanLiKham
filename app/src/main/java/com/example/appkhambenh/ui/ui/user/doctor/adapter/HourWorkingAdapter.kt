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

class HourWorkingAdapter(
    val context: Context,
    private val hours: ArrayList<String>,
) : Adapter<HourWorkingAdapter.ViewHolder>() {

    private var index = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutHour: LinearLayout = itemView.findViewById(R.id.layoutHourWorking)
        val hour: TextView = itemView.findViewById(R.id.hourWorking)
        val viewStart: View = itemView.findViewById(R.id.viewStart)
        val viewEnd: View = itemView.findViewById(R.id.viewEnd)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HourWorkingAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hour_working, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: HourWorkingAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        when(position){
            0 -> {
                holder.viewStart.visibility = View.VISIBLE
                holder.viewEnd.visibility = View.GONE
            }
            (hours.size - 1) -> {
                holder.viewStart.visibility = View.GONE
                holder.viewEnd.visibility = View.VISIBLE
            }
            else -> {
                holder.viewStart.visibility = View.GONE
                holder.viewEnd.visibility = View.GONE
            }
        }

        holder.hour.text = hours[position]

        holder.itemView.setOnClickListener {
            index = position
            notifyDataSetChanged()
        }

        if(index == position){
            holder.layoutHour.setBackgroundResource(R.drawable.bg_time_select)
            holder.hour.setTextColor(context.getColor(R.color.white))
        }else{
            holder.layoutHour.setBackgroundResource(R.drawable.bg_time_un_select)
            holder.hour.setTextColor(context.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = hours.size
}