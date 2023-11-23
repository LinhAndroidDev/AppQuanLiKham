package com.example.appkhambenh.ui.ui.user.csyt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager.widget.ViewPager
import com.example.appkhambenh.R

class DoctorCsytAdapter(
    private val doctors: ArrayList<Int>
) : Adapter<DoctorCsytAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorCsytAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor_csyt, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorCsytAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return doctors.size
    }
}