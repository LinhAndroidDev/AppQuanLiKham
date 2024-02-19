package com.example.appkhambenh.ui.ui.user.hospital.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemDoctorCsytBinding
import com.example.appkhambenh.ui.data.remote.model.Hospital
import com.example.appkhambenh.ui.data.remote.model.Specialist

class DoctorHospitalAdapter(
    private val doctors: ArrayList<Specialist>,
) : Adapter<DoctorHospitalAdapter.ViewHolder>() {
    var onClickItem: ((Boolean) -> Unit)? = null

    inner class ViewHolder(val v: ItemDoctorCsytBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorHospitalAdapter.ViewHolder {
        val v by lazy {
            ItemDoctorCsytBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DoctorHospitalAdapter.ViewHolder, position: Int) {

        holder.v.nameDoctor.text = doctors[position].userCreateName
        holder.v.special.text = doctors[position].nameSpecial

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(true)
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return doctors.size
    }
}