package com.example.appkhambenh.ui.ui.user.navigation.setting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.Ward

class WardAdapter(
    val context: Context,
    private var address: ArrayList<Ward>
) : RecyclerView.Adapter<WardAdapter.ViewHolder>(), Filterable {

    val addressOlds by lazy { address }
    var onClickItem: ((String) -> Unit)? = null
    var index = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.address)
        val layoutAddress: LinearLayout = itemView.findViewById(R.id.layoutAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: WardAdapter.ViewHolder, position: Int) {
        holder.address.text = address[position].name

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(address[position].name)
            index = position
            notifyDataSetChanged()
        }

        if(position == index) {
            holder.layoutAddress.setBackgroundResource(R.color.bg_blue_light)
            holder.address.setTypeface(null, Typeface.BOLD)
        } else {
            holder.layoutAddress.setBackgroundResource(R.color.white)
            holder.address.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun getItemCount(): Int = address.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                address = if(strSearch.isEmpty()){
                    addressOlds
                }else{
                    val list = arrayListOf<Ward>()
                    for(adr in addressOlds){
                        if(adr.name.lowercase().trim().contains(strSearch)){
                            list.add(adr)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = address

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if(p1?.values != null){
                    address = p1.values as ArrayList<Ward>
                    notifyDataSetChanged()
                }
            }

        }
    }

}