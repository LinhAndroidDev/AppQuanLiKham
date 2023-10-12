package com.example.appkhambenh.ui.ui.user.navigation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.FunctionNavigation

class FunctionNavigationAdapter(
    var listFunction: ArrayList<FunctionNavigation>
) : Adapter<FunctionNavigationAdapter.FunctionViewHolder>()
{
    var onClickItem: ((Int) -> Unit)? = null

    class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFunction: ImageView = itemView.findViewById(R.id.imgFunction)
        val nameFunction: TextView = itemView.findViewById(R.id.nameFunction)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FunctionNavigationAdapter.FunctionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_function_navigation, parent, false)
        return FunctionViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FunctionNavigationAdapter.FunctionViewHolder,
        position: Int,
    ) {
        val functionNavigation = listFunction[position]
        holder.imgFunction.setImageResource(functionNavigation.image)
        holder.nameFunction.text = functionNavigation.name

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return listFunction.size
    }

}