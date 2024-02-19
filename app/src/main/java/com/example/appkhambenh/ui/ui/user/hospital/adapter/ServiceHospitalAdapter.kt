package com.example.appkhambenh.ui.ui.user.hospital.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemServiceCsytBinding
import com.example.appkhambenh.ui.data.remote.model.Specialist

class ServiceHospitalAdapter(private val services: ArrayList<Specialist>) :
    Adapter<ServiceHospitalAdapter.ViewHolder>() {

    inner class ViewHolder(val v: ItemServiceCsytBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceHospitalAdapter.ViewHolder {
        val v by lazy {
            ItemServiceCsytBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ServiceHospitalAdapter.ViewHolder, position: Int) {

        holder.v.nameSpecial.text = services[position].nameSpecial

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

    override fun getItemCount(): Int {
        return services.size
    }
}