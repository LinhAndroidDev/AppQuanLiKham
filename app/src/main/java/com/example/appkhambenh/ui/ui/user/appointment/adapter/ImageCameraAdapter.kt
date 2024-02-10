package com.example.appkhambenh.ui.ui.user.appointment.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.appkhambenh.databinding.ItemImageCameraBinding

class ImageCameraAdapter(
    private val uris: ArrayList<Uri>,
    val context: Context,
) : Adapter<ImageCameraAdapter.ViewHolder>() {
    var deleteImage: ((Int) -> Unit)? = null

    inner class ViewHolder(val v: ItemImageCameraBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ImageCameraAdapter.ViewHolder {
        val v by lazy {
            ItemImageCameraBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageCameraAdapter.ViewHolder, position: Int) {

        Glide.with(context)
            .load(uris[position])
            .centerCrop()
            .into(holder.v.imgCamera);

        holder.v.deleteImg.setOnClickListener {
            deleteImage?.invoke(position)
        }
    }

    override fun getItemCount(): Int = uris.size
}