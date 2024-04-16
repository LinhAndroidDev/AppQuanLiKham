package com.example.appkhambenh.ui.ui.user.appointment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemImageCameraBinding
import com.example.appkhambenh.databinding.ItemMemberAppointBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.model.Member

class ImageCameraAdapter(val context: Context) : BaseAdapter<Uri, ItemImageCameraBinding>() {
    var deleteImage: ((Int) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_image_camera

    override fun onBindViewHolder(holder: BaseViewHolder<ItemImageCameraBinding>, position: Int) {
        Glide.with(context)
            .load(items[position])
            .centerCrop()
            .into(holder.v.imgCamera);

        holder.v.deleteImg.setOnClickListener {
            deleteImage?.invoke(position)
        }
    }
}

class MemberAppointAdapter(val context: Context) :
    BaseAdapter<Member, ItemMemberAppointBinding>() {
    private var index = 0
    var addMember: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_member_appoint

    private fun getLastName(name: String): String {
        val lastName = name.split(" ")
        return lastName[lastName.lastIndex]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemMemberAppointBinding>,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        Glide.with(context)
            .load(items[position].avatar)
            .placeholder(R.drawable.user_ad)
            .error(R.drawable.user_ad)
            .into(holder.v.avatar)
        holder.v.nameMember.text = getLastName(items[position].name.toString())

        when (position) {
            items.lastIndex -> {
                holder.v.apply {
                    layoutMember.visibility = View.GONE
                    layoutAddMember.visibility = View.VISIBLE
                }
            }

            else -> {
                holder.v.apply {
                    layoutMember.visibility = View.VISIBLE
                    layoutAddMember.visibility = View.GONE
                    cvMember.setCardBackgroundColor(context.getColor(R.color.white))
                    imgSelected.visibility = View.GONE
                }
            }
        }

        holder.itemView.setOnClickListener {
            if (position != items.lastIndex) {
                index = position
                notifyDataSetChanged()
            } else {
                addMember?.invoke()
            }
        }

        if (position != items.lastIndex) {
            if (position == index) {
                holder.v.apply {
                    cvMember.strokeColor = context.getColor(R.color.background)
                    imgSelected.visibility = View.VISIBLE
                }
            } else {
                holder.v.apply {
                    cvMember.strokeColor = context.getColor(R.color.white)
                    imgSelected.visibility = View.GONE
                }
            }
        }
    }
}