package com.example.appkhambenh.ui.ui.user.appointment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemMemberAppointBinding
import com.example.appkhambenh.ui.model.Member

class MemberAppointAdapter(val context: Context, private val members: ArrayList<Member>) :
    Adapter<MemberAppointAdapter.ViewHolder>() {
    private var index = 0
    var addMember: (() -> Unit)? = null

    inner class ViewHolder(val v: ItemMemberAppointBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MemberAppointAdapter.ViewHolder {
        val v by lazy {
            ItemMemberAppointBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: MemberAppointAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {

        Glide.with(context)
            .load(members[position].avatar)
            .placeholder(R.drawable.user_ad)
            .error(R.drawable.user_ad)
            .into(holder.v.avatar)
        holder.v.nameMember.text = getLastName(members[position].name.toString())

        when (position) {
            members.lastIndex -> {
                holder.v.layoutMember.visibility = View.GONE
                holder.v.layoutAddMember.visibility = View.VISIBLE
            }

            else -> {
                holder.v.layoutMember.visibility = View.VISIBLE
                holder.v.layoutAddMember.visibility = View.GONE
                holder.v.cvMember.setCardBackgroundColor(context.getColor(R.color.white))
                holder.v.imgSelected.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            if (position != members.lastIndex) {
                index = position
                notifyDataSetChanged()
            } else {
                addMember?.invoke()
            }
        }

        if (position != members.lastIndex) {
            if (position == index) {
                holder.v.cvMember.strokeColor = context.getColor(R.color.background)
                holder.v.imgSelected.visibility = View.VISIBLE
            } else {
                holder.v.cvMember.strokeColor = context.getColor(R.color.white)
                holder.v.imgSelected.visibility = View.GONE
            }
        }
    }

    private fun getLastName(name: String): String {
        val lastName = name.split(" ")
        return lastName[lastName.lastIndex]
    }

    override fun getItemCount(): Int = members.size
}