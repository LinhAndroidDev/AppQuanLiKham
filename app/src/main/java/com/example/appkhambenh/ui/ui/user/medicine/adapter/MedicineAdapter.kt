package com.example.appkhambenh.ui.ui.user.medicine.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Medicine
import com.example.appkhambenh.ui.ui.user.medicine.MedicineDetailActivity
import com.squareup.picasso.Picasso

class MedicineAdapter(
    val context: Context,
    private val listMedicine: List<Medicine>
    ): RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageMedicine)
        val name: TextView = itemView.findViewById(R.id.nameMedicine)
        val detail: TextView = itemView.findViewById(R.id.detailMedicine)
        val seeDetail: CardView = itemView.findViewById(R.id.seeDetailMedicine)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MedicineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_medicine, parent, false)
        return MedicineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine: Medicine = listMedicine[position]
        Picasso.get().load(medicine.image)
            .placeholder(R.drawable.loadimage)
            .error(R.drawable.errorimage)
            .into(holder.image)
        holder.name.maxLines = 2
        holder.name.ellipsize = TextUtils.TruncateAt.END
        holder.name.text = medicine.name
        holder.detail.maxLines = 2
        holder.detail.ellipsize = TextUtils.TruncateAt.END
        holder.detail.text = medicine.detail

        holder.seeDetail.setOnClickListener {
            val intent = Intent(context, MedicineDetailActivity::class.java)
            intent.putExtra("medicine",medicine)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listMedicine.size
    }
}