package com.example.appkhambenh.ui.ui.doctor.time_working.adapter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.TimeWorking
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class EditTimeAdapter(
    private val listTimeEdit: ArrayList<TimeWorking>?,
    val context: Context
) : RecyclerView.Adapter<EditTimeAdapter.ViewHolder>() {
    var onSelectDelete: ((Boolean)->Unit)? = null
    var onClickEditHourWorking: ((Int)->Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val time: TextView = itemView.findViewById(R.id.txtTime)
        val imgEditTime: ImageView = itemView.findViewById(R.id.imgEditTime)
        val cbDeleteTime: CheckBox = itemView.findViewById(R.id.cbDeleteTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_time_edit,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time: TimeWorking? = listTimeEdit?.get(position)
        holder.time.text = time?.hour

        holder.imgEditTime.setOnClickListener {
            onClickEditHourWorking?.invoke(position)
        }

        holder.cbDeleteTime.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                onSelectDelete?.invoke(true)
                time?.isSelectDelete = true
            }else{
                time?.isSelectDelete = false
            }
        }

        val list = getListPositionHour(PreferenceKey.LIST_POSITION_HOUR)
        if(list[position] == 1){
            holder.cbDeleteTime.isChecked = false
        }
    }

    private fun saveListPositionHour(list: ArrayList<Int>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    private fun getListPositionHour(key: String?): ArrayList<Int> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<Int>>() {}.type
        return gson.fromJson(json, type)
    }

    override fun getItemCount(): Int {
        return listTimeEdit?.size ?: 0
    }
}