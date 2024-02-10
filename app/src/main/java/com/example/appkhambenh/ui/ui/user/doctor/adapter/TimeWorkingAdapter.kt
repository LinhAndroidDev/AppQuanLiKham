package com.example.appkhambenh.ui.ui.user.doctor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemTimeWorkingBinding
import com.example.appkhambenh.ui.model.Time

class TimeWorkingAdapter(val context: Context, val times: ArrayList<Time>) :
    Adapter<TimeWorkingAdapter.ViewHolder>() {
    private var index = 0
    var onClickTime: ((Int) -> Unit)? = null

    inner class ViewHolder(val v: ItemTimeWorkingBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TimeWorkingAdapter.ViewHolder {
        val v by lazy {
            ItemTimeWorkingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: TimeWorkingAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {

        holder.v.timeDoctor.text = times[position].time
        holder.v.dayDoctor.text = times[position].day

        holder.itemView.setOnClickListener {
            onClickTime?.invoke(position)
            index = position
            notifyDataSetChanged()
        }

        if (index == position) {
            holder.v.layoutTimeWorking.setBackgroundResource(R.drawable.bg_time_select)
            holder.v.timeDoctor.setTextColor(context.getColor(R.color.white))
            holder.v.dayDoctor.setTextColor(context.getColor(R.color.white))
        } else {
            holder.v.layoutTimeWorking.setBackgroundResource(R.drawable.bg_time_un_select)
            holder.v.timeDoctor.setTextColor(context.getColor(R.color.black))
            holder.v.dayDoctor.setTextColor(context.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = times.size
}