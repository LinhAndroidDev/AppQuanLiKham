package com.example.appkhambenh.ui.ui.user.appointment.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.appkhambenh.R

class ImageCameraAdapter(
    private val uris: ArrayList<Uri>,
    val context: Context,
) : Adapter<ImageCameraAdapter.ViewHolder>() {
    var deleteImage: ((Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgCamera)
        val delete: ImageView = itemView.findViewById(R.id.deleteImg)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ImageCameraAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_camera, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageCameraAdapter.ViewHolder, position: Int) {

        Glide.with(context)
            .load(uris[position])
            .centerCrop()
            .into(holder.img);

        holder.delete.setOnClickListener {
            deleteImage?.invoke(position)
        }
    }

    override fun getItemCount(): Int = uris.size
}