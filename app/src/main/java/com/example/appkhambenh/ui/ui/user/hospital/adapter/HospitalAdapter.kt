package com.example.appkhambenh.ui.ui.user.hospital.adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.Filter
import android.widget.Filterable
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemCsytBinding
import com.example.appkhambenh.databinding.ItemDoctorCsytBinding
import com.example.appkhambenh.databinding.ItemServiceCsytBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.model.Hospital
import com.example.appkhambenh.ui.data.remote.model.Service

class HospitalAdapter : BaseAdapter<Hospital, ItemCsytBinding>(), Filterable {
    var onClickItem: ((Hospital) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_csyt

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemCsytBinding>, position: Int) {
        holder.v.nameCsyt.text = items[position].name

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
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
                        it.name.lowercase().trim().contains(strSearch)
                    } as ArrayList<Hospital>
                }
                val filterResults = FilterResults()
                filterResults.values = items

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    items = p1.values as ArrayList<Hospital>
                    notifyDataSetChanged()
                }
            }

        }
    }
}

class DoctorHospitalAdapter : BaseAdapter<Service, ItemDoctorCsytBinding>() {
    var onClickItem: ((Service) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_doctor_csyt

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemDoctorCsytBinding>, position: Int) {
        holder.v.nameDoctor.text = items[position].userCreateName
        holder.v.special.text = items[position].nameSpecial

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
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
}

class ServiceHospitalAdapter : BaseAdapter<Service, ItemServiceCsytBinding>() {

    override fun getLayout(): Int = R.layout.item_service_csyt

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemServiceCsytBinding>, position: Int) {
        holder.v.nameSpecial.text = items[position].nameSpecial

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }
}