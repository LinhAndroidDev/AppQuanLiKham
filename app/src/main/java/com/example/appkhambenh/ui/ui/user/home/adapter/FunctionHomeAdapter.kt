package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.FunctionMain

class FunctionHomeAdapter(
    private val functions: ArrayList<FunctionMain>
) : Adapter<FunctionHomeAdapter.ViewHolder>() {
    var onClickItem: ((Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgFunction)
        val name: TextView = itemView.findViewById(R.id.nameFunction)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FunctionHomeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_function_home, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: FunctionHomeAdapter.ViewHolder, position: Int) {
        val function = functions[position]
        holder.img.setImageResource(function.image)
        holder.name.text = function.name

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when(motionEvent?.actionMasked){
                MotionEvent.ACTION_DOWN->{
                    holder.itemView.alpha = 0.5f
                }
                MotionEvent.ACTION_UP ->{
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(position)
                }
                MotionEvent.ACTION_CANCEL->{
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return functions.size
    }
}