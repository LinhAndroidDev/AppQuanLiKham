package com.example.appkhambenh.ui.ui.user.csyt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager.widget.ViewPager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemDoctorCsytBinding

class DoctorCsytAdapter(
    private val doctors: ArrayList<Int>,
) : Adapter<DoctorCsytAdapter.ViewHolder>() {
    var onClickItem: ((Boolean) -> Unit)? = null

    inner class ViewHolder(val v: ItemDoctorCsytBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorCsytAdapter.ViewHolder {
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
    override fun onBindViewHolder(holder: DoctorCsytAdapter.ViewHolder, position: Int) {

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