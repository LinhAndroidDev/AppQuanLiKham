package com.example.appkhambenh.ui.ui.csyt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Csyt

class CsytAdapter(private val listCsyt: ArrayList<Csyt>) : RecyclerView.Adapter<CsytAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgCsyt)
        val name: TextView = itemView.findViewById(R.id.nameCsyt)
        val address: TextView = itemView.findViewById(R.id.addressCsty)
        val numberVisit: TextView = itemView.findViewById(R.id.numberVisitCsyt)
        val star: TextView = itemView.findViewById(R.id.evaluateCsyt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CsytAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_csyt, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CsytAdapter.ViewHolder, position: Int) {
        val csyt: Csyt = listCsyt[position]
    }

    override fun getItemCount(): Int {
        return listCsyt.size
    }
}