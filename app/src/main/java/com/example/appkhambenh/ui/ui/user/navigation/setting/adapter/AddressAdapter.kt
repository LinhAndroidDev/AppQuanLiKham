package com.example.appkhambenh.ui.ui.user.navigation.setting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemAddressBinding
import com.example.appkhambenh.ui.model.Address

class AddressAdapter() : Adapter<AddressAdapter.ViewHolder>(), Filterable {
    var address: ArrayList<Address> = arrayListOf()
    val addressOlds by lazy { address }
    var onClickItem: ((Address) -> Unit)? = null

    inner class ViewHolder(val v: ItemAddressBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.ViewHolder {
        val v by lazy {
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {
        holder.v.address.text = address[position].name

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(address[position])
        }
    }

    override fun getItemCount(): Int = address.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                address = if (strSearch.isEmpty()) {
                    addressOlds
                } else {
                    val list = arrayListOf<Address>()
                    for (adr in addressOlds) {
                        if (adr.name.lowercase().trim().contains(strSearch)) {
                            list.add(adr)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = address

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    address = p1.values as ArrayList<Address>
                    notifyDataSetChanged()
                }
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetList(list: ArrayList<Address>) {
        address.clear()
        address.addAll(list)
        notifyDataSetChanged()
    }

}