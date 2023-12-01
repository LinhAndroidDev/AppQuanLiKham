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

class DoctorCsytAdapter(
    private val doctors: ArrayList<Int>
) : Adapter<DoctorCsytAdapter.ViewHolder>() {

    var onClickItem: ((Boolean) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorCsytAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor_csyt, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DoctorCsytAdapter.ViewHolder, position: Int) {

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