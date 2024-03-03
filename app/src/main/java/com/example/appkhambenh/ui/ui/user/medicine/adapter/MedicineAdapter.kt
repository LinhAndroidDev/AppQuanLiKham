package com.example.appkhambenh.ui.ui.user.medicine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemMedicineBinding
import com.example.appkhambenh.ui.model.Medicine
import com.example.appkhambenh.ui.utils.strikethroughText

class MedicineAdapter(
    val context: Context,
    ): RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {
    var listMedicine = arrayListOf<Medicine>()

    inner class MedicineViewHolder(val v: ItemMedicineBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MedicineViewHolder {
        val v by lazy { ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        return MedicineViewHolder(v)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        Glide.with(context)
            .load("https://down-vn.img.susercontent.com/file/vn-11134207-7r98o-lkqjvzefdkxn44")
            .placeholder(R.drawable.icon_app_kham_benh)
            .error(R.drawable.icon_app_kham_benh)
            .into(holder.v.imgMedicine)

        holder.v.tvSale.text = strikethroughText("85.000 đ")
    }

    override fun getItemCount(): Int = 9
}