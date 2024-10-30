package com.example.appkhambenh.ui.ui.user.navigation.notification.adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemNotificationBinding
import com.example.appkhambenh.ui.base.BaseAdapter

class NotificationAdapter : BaseAdapter<Int, ItemNotificationBinding>() {

    override fun getLayout(): Int = R.layout.item_notification
    override fun getItemCount(): Int = 10

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemNotificationBinding>, position: Int) {
        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.5f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }
}