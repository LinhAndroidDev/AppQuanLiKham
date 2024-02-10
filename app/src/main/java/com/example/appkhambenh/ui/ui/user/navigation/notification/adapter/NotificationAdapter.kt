package com.example.appkhambenh.ui.ui.user.navigation.notification.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemNotificationBinding
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.utils.setTextNotification

class NotificationAdapter(
    private var notifies: ArrayList<RegisterChecking>?,
) : Adapter<NotificationAdapter.NotifyHolder>() {

    inner class NotifyHolder(val v: ItemNotificationBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NotificationAdapter.NotifyHolder {
        val v by lazy {
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return NotifyHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: NotificationAdapter.NotifyHolder, position: Int) {
        val notification: RegisterChecking? = notifies?.get(position)
        holder.v.txtNotification.text =
            setTextNotification(notification?.reasons!!, notification.date!!, notification.hour!!)

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
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

    fun reverseList() {
        notifies?.reverse()
    }

    override fun getItemCount(): Int {
        return notifies!!.size
    }
}