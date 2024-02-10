package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemSlideBinding

class ImageAdapter(
    val context: Context,
    private val images: ArrayList<Int>,
) : Adapter<ImageAdapter.ViewHolder>() {

    var onClickItem: ((Boolean) -> Unit)? = null

    inner class ViewHolder(val v: ItemSlideBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val v by lazy {
            ItemSlideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.v.slide.setImageResource(images[position])
        holder.v.nameSlide.text = when (position) {
            0 -> context.getString(R.string.doctor_has_experience)
            1 -> context.getString(R.string.priority_to_check)
            2 -> context.getString(R.string.book_online)
            3 -> context.getString(R.string.doctor_has_experience)
            else -> context.getString(R.string.priority_to_check)
        }

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
        return images.size
    }
}