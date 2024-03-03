package com.example.appkhambenh.ui.ui.user.medicine.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemCatalogBinding

class CatalogAdapter(private val context: Context) : Adapter<CatalogAdapter.ViewHolder>() {
    private var catalogs = arrayListOf(
        "Tất cả",
        "Mẹ & bé",
        "Chăm sóc cá nhân",
        "Vật tư y tế",
        "Thuốc kê đơn",
        "Thực phẩm chức năng",
        "Sức khoẻ giới tính",
        "Thuốc không kê đơn"
    )
    private var index = 0

    inner class ViewHolder(val v: ItemCatalogBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogAdapter.ViewHolder {
        val v by lazy {
            ItemCatalogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: CatalogAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        holder.v.nameCatalog.text = catalogs[position]
        holder.itemView.setOnClickListener {
            index = position
            notifyDataSetChanged()
        }

        if (position == index) {
            setTvSelected(holder.v.bgCatalog, holder.v.nameCatalog)
        } else {
            setTvUnSelected(holder.v.bgCatalog, holder.v.nameCatalog)
        }
    }

    private fun setTvSelected(bg: CardView, tv: TextView) {
        bg.cardElevation = 15f
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.setTextColor(context.getColor(R.color.text))
    }

    private fun setTvUnSelected(bg: CardView, tv: TextView) {
        bg.cardElevation = 0f
        tv.typeface = Typeface.DEFAULT
        tv.setTextColor(context.getColor(R.color.text_common))
    }

    override fun getItemCount(): Int = catalogs.size
}