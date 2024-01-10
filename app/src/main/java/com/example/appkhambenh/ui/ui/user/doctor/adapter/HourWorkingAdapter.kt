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
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HourWorkingAdapter(
    val context: Context,
    private val hours: ArrayList<String>,
    private val type: String
) : Adapter<HourWorkingAdapter.ViewHolder>() {

    var onClickTime: ((String) -> Unit)? = null
    private var isClickable = true

    private var index = -1

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

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: HourWorkingAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {

        if(type == InfoDoctorActivity.INFORMATION_DOCTOR) {
            when (position) {
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
        }

        holder.hour.text = hours[position]

        holder.itemView.setOnClickListener {
            if (isClickable) {
                isClickable = false
                index = position
                notifyDataSetChanged()
                GlobalScope.launch(Dispatchers.Main) {
                    delay(300L)
                    isClickable = true
                    onClickTime?.invoke(hours[position])
                }
            }
        }

        if (index == position) {
            holder.layoutHour.setBackgroundResource(R.drawable.bg_time_select)
            holder.hour.setTextColor(context.getColor(R.color.white))
        } else {
            holder.layoutHour.setBackgroundResource(R.drawable.bg_time_un_select)
            holder.hour.setTextColor(context.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = hours.size
}