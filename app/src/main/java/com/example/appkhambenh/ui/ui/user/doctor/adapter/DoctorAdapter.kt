package com.example.appkhambenh.ui.ui.user.doctor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R

class DoctorAdapter(private val doctors: ArrayList<Int>) : Adapter<DoctorAdapter.ViewHolder>() {
    var onClickItem: ((Boolean) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView by lazy { itemView.findViewById(R.id.txtNameDr) }
        val csyt: TextView by lazy { itemView.findViewById(R.id.csytDr) }
        val specialist: TextView by lazy { itemView.findViewById(R.id.specialistDr) }
        val numberVisit: TextView by lazy { itemView.findViewById(R.id.txtNumberVisitDr) }
        val evaluate: TextView by lazy { itemView.findViewById(R.id.txtEvaluateDr) }
        val time: TextView by lazy { itemView.findViewById(R.id.txtLatestMedicalSchedule) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DoctorAdapter.ViewHolder, position: Int) {

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