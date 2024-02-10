package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemFunctionHomeBinding
import com.example.appkhambenh.ui.model.FunctionMain

class FunctionHomeAdapter(
    private val functions: ArrayList<FunctionMain>,
) : Adapter<FunctionHomeAdapter.ViewHolder>() {
    var onClickItem: ((Int) -> Unit)? = null

    inner class ViewHolder(val v: ItemFunctionHomeBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FunctionHomeAdapter.ViewHolder {
        val v by lazy {
            ItemFunctionHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: FunctionHomeAdapter.ViewHolder, position: Int) {
        val function = functions[position]
        holder.v.imgFunction.setImageResource(function.image)
        holder.v.nameFunction.text = function.name

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(position)
                }

                MotionEvent.ACTION_CANCEL -> {
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