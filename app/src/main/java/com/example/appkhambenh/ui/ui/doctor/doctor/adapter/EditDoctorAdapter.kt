package com.example.appkhambenh.ui.ui.doctor.doctor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Doctor
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class EditDoctorAdapter(
    private val listDoctor: ArrayList<Doctor>,
    val context: Context
) : RecyclerView.Adapter<EditDoctorAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameDoctor: TextView = itemView.findViewById(R.id.txtNameDoctor)
        val imageDoctor: CircleImageView = itemView.findViewById(R.id.imgDoctor)
        val menu: LinearLayout = itemView.findViewById(R.id.menuDoctor)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EditDoctorAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_doctor, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EditDoctorAdapter.ViewHolder, position: Int) {
        val doctor: Doctor = listDoctor[position]
        holder.nameDoctor.text = doctor.name
        Picasso.get().load(doctor.image)
            .placeholder(R.drawable.icon_doctor)
            .error(R.drawable.icon_doctor)
            .into(holder.imageDoctor)

        holder.menu.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listDoctor.size
    }
}