package com.example.appkhambenh.ui.ui.user.service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemExaminationServiceBinding

class ExaminationServiceAdapter : Adapter<ExaminationServiceAdapter.ViewHolder>() {
    var services = arrayListOf<Int>()

    inner class ViewHolder(val v: ItemExaminationServiceBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ExaminationServiceAdapter.ViewHolder {
        val v = ItemExaminationServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExaminationServiceAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = services.size
}