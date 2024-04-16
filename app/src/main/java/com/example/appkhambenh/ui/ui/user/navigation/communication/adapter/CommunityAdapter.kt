package com.example.appkhambenh.ui.ui.user.navigation.communication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemCommunityBinding
import com.example.appkhambenh.databinding.ItemDoctorCommentBinding
import com.example.appkhambenh.databinding.ItemPatientCommentBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.data.remote.model.CommentModel

class CommunityAdapter(private val context: Context) : BaseAdapter<Int, ItemCommunityBinding>() {
    var onClickItem: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_community
    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: BaseViewHolder<ItemCommunityBinding>, position: Int) {
        Glide.with(context)
            .load(R.drawable.img_doctor)
            .placeholder(R.drawable.user_ad)
            .error(R.drawable.user_ad)
            .into(holder.v.layoutDoctorComment.avtDoctor)

        holder.itemView.setOnClickListener {
            onClickItem?.invoke()
        }
    }
}

class CommentQuestionAdapter : Adapter<ViewHolder>() {
    var items = arrayListOf<CommentModel>()

    companion object {
        const val PATIENT = 0
        const val DOCTOR = 1
    }

    inner class DoctorCommentHolder(val v: ItemDoctorCommentBinding) : ViewHolder(v.root)

    inner class PatientCommentHolder(val v: ItemPatientCommentBinding) : ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == PATIENT) {
            PatientCommentHolder(
                ItemPatientCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            DoctorCommentHolder(
                ItemDoctorCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder.javaClass == PatientCommentHolder::class.java) {
            val holderPatient = holder as PatientCommentHolder
            holderPatient.v.tvPatientComment.text = items[position].content
        } else {
            val holderDoctor = holder as DoctorCommentHolder
            holderDoctor.v.tvDoctorComment.text = items[position].content
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].userSend == 0) PATIENT else DOCTOR
    }

    override fun getItemCount(): Int = items.size
}