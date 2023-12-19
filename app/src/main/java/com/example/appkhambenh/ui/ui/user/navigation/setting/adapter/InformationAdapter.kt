package com.example.appkhambenh.ui.ui.user.navigation.setting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R

class InformationAdapter(private var infors: ArrayList<String>) :
    Adapter<InformationAdapter.ViewHolder>(), Filterable {
    var onClickItem: ((String) -> Unit)? = null
    val inforOlds by lazy { infors }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameInfo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InformationAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_information, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InformationAdapter.ViewHolder, position: Int) {
        holder.name.text = infors[position]

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(infors[position])
        }
    }

    override fun getItemCount(): Int = infors.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                infors = if(strSearch.isEmpty()){
                    inforOlds
                }else{
                    val list = arrayListOf<String>()
                    for(adr in inforOlds){
                        if(adr.lowercase().trim().contains(strSearch)){
                            list.add(adr)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = infors

                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if(p1?.values != null){
                    infors = p1.values as ArrayList<String>
                    notifyDataSetChanged()
                }
            }

        }
    }
}