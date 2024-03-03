package com.example.appkhambenh.ui.ui.user.navigation.communication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemCommunityBinding

class CommunityAdapter(private val context: Context) : Adapter<CommunityAdapter.ViewHolder>() {

    inner class ViewHolder(val v: ItemCommunityBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.ViewHolder {
        val v by lazy {
            ItemCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommunityAdapter.ViewHolder, position: Int) {
        Glide.with(context)
            .load(R.drawable.img_doctor)
            .placeholder(R.drawable.user_ad)
            .error(R.drawable.user_ad)
            .into(holder.v.avtDoctor)
    }

    override fun getItemCount(): Int = 5
}