package com.example.appkhambenh.ui.ui.user.doctor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDoctorBinding
import com.example.appkhambenh.databinding.ItemHourWorkingBinding
import com.example.appkhambenh.databinding.ItemTimeWorkingBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.model.DoctorModel
import com.example.appkhambenh.ui.data.remote.model.HourModel
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DoctorAdapter : BaseAdapter<DoctorModel, ItemDoctorBinding>(), Filterable {
    var onClickItem: ((DoctorModel) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_doctor

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemDoctorBinding>, position: Int) {
        holder.v.tvNameDortor.text = items[position].name
        holder.v.tvNameHospital.text = items[position].hospitalName
        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.5f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(items[position])
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                items = if (strSearch.isEmpty()) {
                    itemOlds
                } else {
                    itemOlds.filter {
                        it.name?.lowercase()?.trim()!!.contains(strSearch)
                    } as ArrayList<DoctorModel>
                }
                val filterResults = FilterResults()
                filterResults.values = items

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    items = p1.values as ArrayList<DoctorModel>
                    notifyDataSetChanged()
                }
            }

        }
    }
}

class HourWorkingAdapter(
    private val context: Context,
    private val type: String,
) : BaseAdapter<HourModel, ItemHourWorkingBinding>() {
    var onClickTime: ((HourModel) -> Unit)? = null
    private var isClickable = true
    private var index = -1

    override fun getLayout(): Int = R.layout.item_hour_working

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemHourWorkingBinding>,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        if (type == InfoDoctorActivity.INFORMATION_DOCTOR) {
            when (position) {
                0 -> {
                    holder.v.viewStart.visibility = View.VISIBLE
                    holder.v.viewEnd.visibility = View.GONE
                }

                (items.size - 1) -> {
                    holder.v.viewStart.visibility = View.GONE
                    holder.v.viewEnd.visibility = View.VISIBLE
                }

                else -> {
                    holder.v.viewStart.visibility = View.GONE
                    holder.v.viewEnd.visibility = View.GONE
                }
            }
        }

        holder.v.hourWorking.text = items[position].hour

        holder.itemView.setOnClickListener {
            if (isClickable) {
                isClickable = false
                index = position
                notifyDataSetChanged()
                GlobalScope.launch(Dispatchers.Main) {
                    delay(300L)
                    isClickable = true
                    onClickTime?.invoke(items[position])
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
}

class TimeWorkingAdapter(private val context: Context) :
    BaseAdapter<Time, ItemTimeWorkingBinding>() {
    private var index = 0
    var onClickTime: ((Int) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_time_working

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTimeWorkingBinding>,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        holder.v.timeDoctor.text = items[position].time
        holder.v.dayDoctor.text = items[position].day

        holder.itemView.setOnClickListener {
            onClickTime?.invoke(position)
            index = position
            notifyDataSetChanged()
        }

        if (index == position) {
            holder.v.apply {
                layoutTimeWorking.setBackgroundResource(R.drawable.bg_time_select)
                timeDoctor.setTextColor(context.getColor(R.color.white))
                dayDoctor.setTextColor(context.getColor(R.color.white))
            }
        } else {
            holder.v.apply {
                layoutTimeWorking.setBackgroundResource(R.drawable.bg_time_un_select)
                timeDoctor.setTextColor(context.getColor(R.color.black))
                dayDoctor.setTextColor(context.getColor(R.color.black))
            }
        }
    }
}