package com.example.appkhambenh.ui.ui.user.appointment.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Service

class ServiceAdapter(
    private val listService: ArrayList<Service>,
    val context: Context,
    var onSelectFunction: ((String)->Unit)? = null
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameService: TextView = itemView.findViewById(R.id.txt_name_function)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_function_appointment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServiceAdapter.ViewHolder, position: Int) {
        val service: Service = listService[position]
        holder.nameService.text = service.nameService

        holder.itemView.setOnClickListener {
            onSelectFunction?.invoke(service.nameService)
        }
    }

    override fun getItemCount(): Int {
        return listService.size
    }
}