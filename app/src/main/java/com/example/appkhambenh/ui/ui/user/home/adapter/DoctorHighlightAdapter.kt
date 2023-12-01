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
import com.example.appkhambenh.ui.model.Doctor

class DoctorHighlightAdapter(private val doctors: ArrayList<Doctor>) :
    Adapter<DoctorHighlightAdapter.ViewHolder>() {

    var onClickItem: ((Boolean) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgHighlightDoctor)
        val name: TextView = itemView.findViewById(R.id.nameHighlight)
        val specialist: TextView = itemView.findViewById(R.id.specialistHighlight)
        val hospital: TextView = itemView.findViewById(R.id.hospitalHighlight)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorHighlightAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_highlight_doctor, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DoctorHighlightAdapter.ViewHolder, position: Int) {
        holder.img.setImageResource(doctors[position].image!!)
        holder.name.text = "${doctors[position].position} ${doctors[position].name}"
        holder.specialist.text = doctors[position].specialist
        holder.hospital.text = doctors[position].hospital

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when(motionEvent?.actionMasked){
                MotionEvent.ACTION_DOWN->{
                    holder.itemView.alpha = 0.3f
                }
                MotionEvent.ACTION_UP ->{
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(true)
                }
                MotionEvent.ACTION_CANCEL->{
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