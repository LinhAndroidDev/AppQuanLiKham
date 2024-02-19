package com.example.appkhambenh.ui.ui.user.hospital.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.databinding.ItemCsytBinding
import com.example.appkhambenh.ui.data.remote.model.Hospital

class HospitalAdapter(private val hospitals: ArrayList<Hospital>) :
    RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {
    var onClickItem: ((Hospital) -> Unit)? = null

    inner class ViewHolder(val v: ItemCsytBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v by lazy {
            ItemCsytBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.v.nameCsyt.text = hospitals[position].name

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(hospitals[position])
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }
}