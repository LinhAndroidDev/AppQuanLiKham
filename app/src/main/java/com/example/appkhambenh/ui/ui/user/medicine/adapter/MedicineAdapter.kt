package com.example.appkhambenh.ui.ui.user.medicine.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemCatalogBinding
import com.example.appkhambenh.databinding.ItemImageDetailMedicineBinding
import com.example.appkhambenh.databinding.ItemMedicineBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.utils.strikethroughText

class MedicineAdapter(private val context: Context) : BaseAdapter<Int, ItemMedicineBinding>() {
    var onClickItem: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_medicine
    override fun getItemCount(): Int = 9

    override fun onBindViewHolder(holder: BaseViewHolder<ItemMedicineBinding>, position: Int) {
        Glide.with(context)
            .load("https://down-vn.img.susercontent.com/file/vn-11134207-7r98o-lkqjvzefdkxn44")
            .placeholder(R.drawable.icon_app_kham_benh)
            .error(R.drawable.icon_app_kham_benh)
            .into(holder.v.imgMedicine)

        holder.v.tvSale.text = strikethroughText("85.000 đ")

        holder.itemView.setOnClickListener {
            onClickItem?.invoke()
        }
    }
}

class CatalogAdapter(private val context: Context) : BaseAdapter<String, ItemCatalogBinding>() {
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

    init {
        items.addAll(catalogs)
    }

    override fun getLayout(): Int = R.layout.item_catalog

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemCatalogBinding>,
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
}

class ImageDetailMedicineAdapter(private val context: Context) :
    BaseAdapter<String, ItemImageDetailMedicineBinding>() {

    override fun getLayout(): Int = R.layout.item_image_detail_medicine

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemImageDetailMedicineBinding>,
        position: Int,
    ) {
        Glide.with(context)
            .load(items[position])
            .placeholder(R.drawable.loadimage)
            .error(R.drawable.loadimage)
            .into(holder.v.imgDetailMedicine)
    }
}