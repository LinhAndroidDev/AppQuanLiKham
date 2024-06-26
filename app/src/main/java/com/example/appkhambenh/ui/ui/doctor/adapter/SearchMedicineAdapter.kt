package com.example.appkhambenh.ui.ui.doctor.adapter

import android.view.View
import androidx.core.view.isVisible
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemSearchMedicineBinding
import com.example.appkhambenh.ui.base.BaseAdapter

data class SearchMedicine(
    val name: String,
    var stateChecked: Boolean = false,
    var quantity: Int = 0
)

class SearchMedicineAdapter : BaseAdapter<SearchMedicine, ItemSearchMedicineBinding>() {
    var listMedicineSelected = arrayListOf<Int>()

    override fun getLayout(): Int = R.layout.item_search_medicine

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemSearchMedicineBinding>,
        position: Int,
    ) {
        val medicine = items[position]
        holder.v.apply {
            nameMedicine.text = medicine.name
            viewBottom.isVisible = position == items.size - 1
        }

        holder.itemView.setOnClickListener {
            medicine.stateChecked = !medicine.stateChecked
            if(medicine.stateChecked) {
                holder.v.checkMedicine.visibility = View.VISIBLE
                listMedicineSelected.add(position)
            } else {
                holder.v.checkMedicine.visibility = View.INVISIBLE
                listMedicineSelected.removeAt(listMedicineSelected.indexOf(position))
            }
        }
    }
}