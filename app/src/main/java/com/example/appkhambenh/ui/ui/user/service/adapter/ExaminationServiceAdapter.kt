package com.example.appkhambenh.ui.ui.user.service.adapter

import android.annotation.SuppressLint
import android.widget.Filter
import android.widget.Filterable
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemExaminationServiceBinding
import com.example.appkhambenh.databinding.ItemSpecialistBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.model.Specialist

class SpecialAdapter: BaseAdapter<Specialist, ItemSpecialistBinding>(), Filterable {
    var onClickItem: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_specialist

    override fun onBindViewHolder(holder: BaseViewHolder<ItemSpecialistBinding>, position: Int) {
        holder.v.nameSpecialist.text = items[position].name
        holder.itemView.setOnClickListener {
            onClickItem?.invoke()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                items = if (strSearch.isEmpty()) {
                    itemOlds
                } else {
                    val list = arrayListOf<Specialist>()
                    for (special in itemOlds) {
                        if (special.name?.lowercase()?.trim()!!.contains(strSearch)) {
                            list.add(special)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = items

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    items = p1.values as ArrayList<Specialist>
                    notifyDataSetChanged()
                }
            }

        }
    }
}

class ExaminationServiceAdapter : BaseAdapter<Int, ItemExaminationServiceBinding>() {

    override fun getLayout(): Int = R.layout.item_examination_service
    override fun getItemCount(): Int = 9

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemExaminationServiceBinding>,
        position: Int,
    ) {

    }
}