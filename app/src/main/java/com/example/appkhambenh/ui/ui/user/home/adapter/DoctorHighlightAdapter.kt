package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.LayoutHighlightDoctorBinding
import com.example.appkhambenh.ui.model.Doctor

class DoctorHighlightAdapter(private val doctors: ArrayList<Doctor>) :
    Adapter<DoctorHighlightAdapter.ViewHolder>() {

    var onClickItem: ((Boolean) -> Unit)? = null

    inner class ViewHolder(val v: LayoutHighlightDoctorBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorHighlightAdapter.ViewHolder {
        val v by lazy {
            LayoutHighlightDoctorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DoctorHighlightAdapter.ViewHolder, position: Int) {
        holder.v.imgHighlightDoctor.setImageResource(doctors[position].image!!)
        holder.v.nameHighlight.text = "${doctors[position].position} ${doctors[position].name}"
        holder.v.specialistHighlight.text = doctors[position].specialist
        holder.v.hospitalHighlight.text = doctors[position].hospital

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