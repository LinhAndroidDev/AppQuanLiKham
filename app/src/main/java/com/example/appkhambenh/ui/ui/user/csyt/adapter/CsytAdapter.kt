package com.example.appkhambenh.ui.ui.user.csyt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.databinding.ItemCsytBinding
import com.example.appkhambenh.ui.model.Csyt

class CsytAdapter(private val listCsyt: ArrayList<Csyt>) :
    RecyclerView.Adapter<CsytAdapter.ViewHolder>() {
    var onClickItem: ((Boolean) -> Unit)? = null

    inner class ViewHolder(val v: ItemCsytBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v by lazy {
            ItemCsytBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(true)
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return listCsyt.size
    }
}