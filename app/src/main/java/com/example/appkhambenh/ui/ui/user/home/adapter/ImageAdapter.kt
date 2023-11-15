package com.example.appkhambenh.ui.ui.user.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager2.widget.ViewPager2
import com.example.appkhambenh.R

class ImageAdapter(
    private val images: ArrayList<Int>,
): Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slide: ImageView = itemView.findViewById(R.id.slide)
        val name: TextView = itemView.findViewById(R.id.nameSlide)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.slide.setImageResource(images[position])
        holder.name.text = when (position) {
            0 -> "Bác Sĩ có nhiều kinh nghiệm"
            1 -> "Ưu tiên Check in qua app"
            2 -> "Đặt lịch khám online"
            3 -> "Bác Sĩ có nhiều kinh nghiệm"
            else -> "Ưu tiên Check in qua app"
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}