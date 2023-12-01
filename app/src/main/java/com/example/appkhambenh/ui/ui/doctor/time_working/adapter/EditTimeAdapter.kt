package com.example.appkhambenh.ui.ui.doctor.time_working.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.TimeWorking

class EditTimeAdapter(
    private val listTimeEdit: ArrayList<TimeWorking>?,
    val context: Context,
) : RecyclerView.Adapter<EditTimeAdapter.ViewHolder>() {
    var onSelectDelete: ((String) -> Unit)? = null
    var onClickEditHourWorking: ((Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView by lazy { itemView.findViewById(R.id.txtTime) }
        val imgEditTime: ImageView by lazy { itemView.findViewById(R.id.imgEditTime) }
        val imgDeleteTime: ImageView by lazy { itemView.findViewById(R.id.imgDeleteTime) }
        val txtReserved: TextView by lazy { itemView.findViewById(R.id.txtReserved) }
        val layoutItem: CardView by lazy { itemView.findViewById(R.id.layoutItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_time_edit, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time: TimeWorking? = listTimeEdit?.get(position)

        if (time?.is_registered == 1) {
            holder.txtReserved.visibility = View.VISIBLE
            holder.imgEditTime.visibility = View.INVISIBLE
            holder.imgEditTime.isEnabled = false
            holder.imgDeleteTime.visibility = View.INVISIBLE
            holder.imgDeleteTime.isEnabled = false
            holder.layoutItem.setCardBackgroundColor(context.resources.getColor(R.color.grey))
        }

        holder.time.text = time?.hour

        holder.imgEditTime.setOnClickListener {
            onClickEditHourWorking?.invoke(position)
        }

        holder.imgDeleteTime.setOnClickListener {
            onSelectDelete?.invoke(time?.hour.toString())
        }

    }

    override fun getItemCount(): Int {
        return listTimeEdit?.size ?: 0
    }
}