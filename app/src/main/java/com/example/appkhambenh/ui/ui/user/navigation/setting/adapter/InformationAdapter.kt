package com.example.appkhambenh.ui.ui.user.navigation.setting.adapter

import android.annotation.SuppressLint
import android.widget.Filter
import android.widget.Filterable
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemInformationBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class InformationAdapter : BaseAdapter<String, ItemInformationBinding>(), Filterable {
    var onClickItem: ((String) -> Unit)? = null
    val infoOlds by lazy { items }

    override fun getLayout(): Int = R.layout.item_information

    override fun onBindViewHolder(holder: BaseViewHolder<ItemInformationBinding>, position: Int) {
        holder.v.nameInfo.text = items[position]
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(items[position])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                items = if (strSearch.isEmpty()) {
                    infoOlds
                } else {
                    val list = arrayListOf<String>()
                    for (adr in infoOlds) {
                        if (adr.lowercase().trim().contains(strSearch)) {
                            list.add(adr)
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
                    items = p1.values as ArrayList<String>
                    notifyDataSetChanged()
                }
            }

        }
    }
}