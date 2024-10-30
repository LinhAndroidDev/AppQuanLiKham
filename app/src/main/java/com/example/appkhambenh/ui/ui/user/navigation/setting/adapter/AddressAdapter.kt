package com.example.appkhambenh.ui.ui.user.navigation.setting.adapter

import android.annotation.SuppressLint
import android.widget.Filter
import android.widget.Filterable
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemAddressBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.model.Address

class AddressAdapter : BaseAdapter<Address, ItemAddressBinding>(), Filterable {
    var onClickItem: ((Address) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_address

    override fun onBindViewHolder(holder: BaseViewHolder<ItemAddressBinding>, position: Int) {
        holder.v.address.text = items[position].name
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(items[position])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                items = if (strSearch.isEmpty()) {
                    itemOlds
                } else {
                    itemOlds.filter {
                        it.name.lowercase().trim().contains(strSearch)
                    } as ArrayList<Address>
                }
                val filterResults = FilterResults()
                filterResults.values = items

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    items = p1.values as ArrayList<Address>
                    notifyDataSetChanged()
                }
            }

        }
    }
}