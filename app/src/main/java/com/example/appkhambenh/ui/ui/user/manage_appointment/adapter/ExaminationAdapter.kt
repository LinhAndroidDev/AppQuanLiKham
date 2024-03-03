package com.example.appkhambenh.ui.ui.user.manage_appointment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemExaminationBinding

class ExaminationAdapter(private var examinations: ArrayList<Int>) :
    Adapter<ExaminationAdapter.ViewHolder>() {

    inner class ViewHolder(val v: ItemExaminationBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ExaminationAdapter.ViewHolder {
        val v by lazy {
            ItemExaminationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExaminationAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = examinations.size
}