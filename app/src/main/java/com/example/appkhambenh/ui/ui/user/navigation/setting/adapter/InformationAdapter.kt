package com.example.appkhambenh.ui.ui.user.navigation.setting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.ui.user.navigation.setting.UpdateInformationActivity
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl

class InformationAdapter(
    private var infors: ArrayList<String>,
    val type: String,
    val context: Context,
) : Adapter<InformationAdapter.ViewHolder>(), Filterable {
    var onClickItem: ((String) -> Unit)? = null
    val inforOlds by lazy { infors }
    private val sharedPrefer = SharePreferenceRepositoryImpl(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameInfo)
        val layout: FrameLayout = itemView.findViewById(R.id.layoutCoverInfo)
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

//        when (type) {
//            UpdateInformationActivity.ETHNICS -> {
//                checkIndex(sharedPrefer.getIndexEthnics(), position, holder.layout, holder.name)
//            }
//
//            UpdateInformationActivity.NATIONALITY -> {
//                checkIndex(sharedPrefer.getIndexNationality(), position, holder.layout, holder.name)
//            }
//
//            UpdateInformationActivity.JOB -> {
//                checkIndex(sharedPrefer.getIndexJob(), position, holder.layout, holder.name)
//            }
//        }

        holder.itemView.setOnClickListener {
            when (type) {
                UpdateInformationActivity.ETHNICS -> {
                    onClickItem?.invoke(infors[position])
//                    sharedPrefer.saveIndexEthnics(position)
                }

                UpdateInformationActivity.NATIONALITY -> {
                    onClickItem?.invoke(infors[position])
//                    sharedPrefer.saveIndexNationality(position)
                }

                UpdateInformationActivity.JOB -> {
                    onClickItem?.invoke(infors[position])
//                    sharedPrefer.saveIndexJob(position)
                }
            }
        }
    }

    private fun checkIndex(index: Int, position: Int, layout: FrameLayout, txt: TextView) {
        if (index == position) {
            layout.setBackgroundResource(R.color.bg_blue_light)
            txt.typeface = Typeface.DEFAULT_BOLD
        } else {
            layout.setBackgroundResource(R.color.white)
            txt.typeface = Typeface.DEFAULT
        }
    }

    fun clearList() {
        infors.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = infors.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                infors = if (strSearch.isEmpty()) {
                    inforOlds
                } else {
                    val list = arrayListOf<String>()
                    for (adr in inforOlds) {
                        if (adr.lowercase().trim().contains(strSearch)) {
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
                if (p1?.values != null) {
                    infors = p1.values as ArrayList<String>
                    notifyDataSetChanged()
                }
            }

        }
    }
}