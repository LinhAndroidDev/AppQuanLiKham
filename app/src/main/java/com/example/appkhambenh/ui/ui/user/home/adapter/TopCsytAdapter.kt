package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.databinding.ItemTopCsytBinding

class TopCsytAdapter(val context: Context, private val csyts: ArrayList<Int>) :
    Adapter<TopCsytAdapter.ViewHolder>() {

    var onCLickItem: ((Boolean) -> Unit)? = null

    inner class ViewHolder(val v: ItemTopCsytBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCsytAdapter.ViewHolder {
        val v by lazy {
            ItemTopCsytBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TopCsytAdapter.ViewHolder, position: Int) {
        when (position) {
            0 -> holder.v.viewStartTopCsyt.visibility = View.VISIBLE
            else -> holder.v.viewStartTopCsyt.visibility = View.GONE
        }

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onCLickItem?.invoke(true)
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int = csyts.size
}