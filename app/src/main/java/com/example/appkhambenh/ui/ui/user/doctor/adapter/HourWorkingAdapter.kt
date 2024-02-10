package com.example.appkhambenh.ui.ui.user.doctor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemHourWorkingBinding
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HourWorkingAdapter(
    val context: Context,
    private val hours: ArrayList<String>,
    private val type: String,
) : Adapter<HourWorkingAdapter.ViewHolder>() {
    var onClickTime: ((String) -> Unit)? = null
    private var isClickable = true
    private var index = -1

    inner class ViewHolder(val v: ItemHourWorkingBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HourWorkingAdapter.ViewHolder {
        val v by lazy {
            ItemHourWorkingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: HourWorkingAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {

        if (type == InfoDoctorActivity.INFORMATION_DOCTOR) {
            when (position) {
                0 -> {
                    holder.v.viewStart.visibility = View.VISIBLE
                    holder.v.viewEnd.visibility = View.GONE
                }

                (hours.size - 1) -> {
                    holder.v.viewStart.visibility = View.GONE
                    holder.v.viewEnd.visibility = View.VISIBLE
                }

                else -> {
                    holder.v.viewStart.visibility = View.GONE
                    holder.v.viewEnd.visibility = View.GONE
                }
            }
        }

        holder.v.hourWorking.text = hours[position]

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
            holder.v.layoutHourWorking.setBackgroundResource(R.drawable.bg_time_select)
            holder.v.hourWorking.setTextColor(context.getColor(R.color.white))
        } else {
            holder.v.layoutHourWorking.setBackgroundResource(R.drawable.bg_time_un_select)
            holder.v.hourWorking.setTextColor(context.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = hours.size
}