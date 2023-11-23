package com.example.appkhambenh.ui.ui.user.csyt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Csyt

class CsytAdapter(private val listCsyt: ArrayList<Csyt>) : RecyclerView.Adapter<CsytAdapter.ViewHolder>() {

    var onClickItem: ((Boolean) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView by lazy { itemView.findViewById(R.id.imgCsyt) }
        val name: TextView by lazy { itemView.findViewById(R.id.nameCsyt) }
        val address: TextView by lazy { itemView.findViewById(R.id.addressCsty) }
        val numberVisit: TextView by lazy { itemView.findViewById(R.id.numberVisitCsyt) }
        val star: TextView by lazy { itemView.findViewById(R.id.evaluateCsyt) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_csyt, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(true)
        }
    }

    override fun getItemCount(): Int {
        return listCsyt.size
    }
}