package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R

class TopCsytAdapter(val context: Context, private val csyts: ArrayList<Int>) : Adapter<TopCsytAdapter.ViewHolder>() {

    var onCLickItem: ((Boolean) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewStart: View = itemView.findViewById(R.id.viewStartTopCsyt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCsytAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_top_csyt, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TopCsytAdapter.ViewHolder, position: Int) {
        when(position){
            0 -> holder.viewStart.visibility = View.VISIBLE
            else -> holder.viewStart.visibility = View.GONE
        }

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when(motionEvent?.actionMasked){
                MotionEvent.ACTION_DOWN->{
                    holder.itemView.alpha = 0.3f
                }
                MotionEvent.ACTION_UP ->{
                    holder.itemView.alpha = 1f
                    onCLickItem?.invoke(true)
                }
                MotionEvent.ACTION_CANCEL->{
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int = csyts.size
}