package com.example.appkhambenh.ui.ui.user.navigation.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.utils.setTextNotification

class NotificationAdapter(
    var listNotification: ArrayList<RegisterChecking>?
) : Adapter<NotificationAdapter.NotifiViewHolder>() {

    class NotifiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNotification: TextView = itemView.findViewById(R.id.txtNotification)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NotificationAdapter.NotifiViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotifiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.NotifiViewHolder, position: Int) {
        val notification: RegisterChecking? = listNotification?.get(position)
        holder.txtNotification.text = setTextNotification(notification?.reasons!!, notification.date!!, notification.hour!!)
    }

    fun reverseList(){
        listNotification?.reverse()
    }

    override fun getItemCount(): Int {
        return listNotification!!.size
    }
}