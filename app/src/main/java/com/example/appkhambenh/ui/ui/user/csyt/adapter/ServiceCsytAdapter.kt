package com.example.appkhambenh.ui.ui.user.csyt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R

class ServiceCsytAdapter(val services: ArrayList<Int>) : Adapter<ServiceCsytAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceCsytAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service_csyt, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServiceCsytAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return services.size
    }
}