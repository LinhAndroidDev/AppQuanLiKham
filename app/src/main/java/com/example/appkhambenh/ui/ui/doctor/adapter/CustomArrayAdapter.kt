package com.example.appkhambenh.ui.ui.doctor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.utils.removeAccents


class CustomArrayAdapter(context: Context?, resource: Int, private val objects: List<String?>?) :
    ArrayAdapter<String?>(context!!, resource, objects!!) {

    private var filteredData: List<String?>? = objects

    override fun getCount(): Int = if(filteredData?.isNotEmpty() == true) filteredData!!.size else 0

    override fun getItem(position: Int): String? {
        return filteredData?.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_auto_text, parent, false)
        }
        val textView = convertView!!.findViewById<TextView>(R.id.textAuto)
        textView.text = getItem(position)
        return convertView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val searchStr = removeAccents(p0.toString())
                if(p0 != null) {
                    val suggestions = objects?.filter { removeAccents(it.toString()).contains(searchStr, true) }
                    filterResults.values = suggestions
                    filterResults.count = suggestions!!.size
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredData = p1?.values as List<String>
                notifyDataSetChanged()
            }

        }
    }
}