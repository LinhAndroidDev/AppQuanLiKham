package com.example.appkhambenh.ui.ui.user.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.MedicalHandbook
import com.squareup.picasso.Picasso

class MedicalHandBookAdapter(
    val context: Context,
    private val handbooks: ArrayList<MedicalHandbook>
) : Adapter<MedicalHandBookAdapter.ViewHolder>() {

    var url: ((String) ->Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgHandbook)
        val title: TextView = itemView.findViewById(R.id.titleHandbook)
        val time: TextView = itemView.findViewById(R.id.timeHandbook)
        val minutes: TextView = itemView.findViewById(R.id.minutesHandbook)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MedicalHandBookAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medical_handbook, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicalHandBookAdapter.ViewHolder, position: Int) {
        Picasso.get().load(handbooks[position].image)
            .resize(
                context.resources.getDimension(R.dimen.dimen_120).toInt(),
                context.resources.getDimension(R.dimen.dimen_80).toInt()
            )
            .centerCrop().into(holder.image)
        holder.title.text = handbooks[position].title
        holder.time.text = handbooks[position].time
        holder.minutes.text = handbooks[position].minutes

        holder.itemView.setOnClickListener {
            when(position){
                0 -> url?.invoke("https://ivie.vn/-nguyen-nhan-buou-co-o-nam-gioi-va-cach-dieu-tri-0")
                1 -> url?.invoke("https://ivie.vn/-dia-chi-xet-nghiem-di-nguyen-tai-ha-noi-0")
                else -> url?.invoke("https://ivie.vn/xet-nghiem-lao-phoi-gia-bao-nhieu-0")
            }
        }
    }

    override fun getItemCount(): Int {
        return handbooks.size
    }
}