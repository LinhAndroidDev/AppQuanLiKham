package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemMedicalHandbookBinding
import com.example.appkhambenh.ui.model.MedicalHandbook
import com.squareup.picasso.Picasso

class MedicalHandBookAdapter(
    val context: Context,
    private val handbooks: ArrayList<MedicalHandbook>,
) : Adapter<MedicalHandBookAdapter.ViewHolder>() {
    var url: ((String) -> Unit)? = null

    inner class ViewHolder(val v: ItemMedicalHandbookBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MedicalHandBookAdapter.ViewHolder {
        val v by lazy {
            ItemMedicalHandbookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MedicalHandBookAdapter.ViewHolder, position: Int) {
        Picasso.get().load(handbooks[position].image)
            .resize(
                context.resources.getDimension(R.dimen.dimen_120).toInt(),
                context.resources.getDimension(R.dimen.dimen_80).toInt()
            )
            .centerCrop().into(holder.v.imgHandbook)
        holder.v.titleHandbook.text = handbooks[position].title
        holder.v.timeHandbook.text = handbooks[position].time
        holder.v.minutesHandbook.text = handbooks[position].minutes

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    when (position) {
                        0 -> url?.invoke("https://ivie.vn/-nguyen-nhan-buou-co-o-nam-gioi-va-cach-dieu-tri-0")
                        1 -> url?.invoke("https://ivie.vn/-dia-chi-xet-nghiem-di-nguyen-tai-ha-noi-0")
                        else -> url?.invoke("https://ivie.vn/xet-nghiem-lao-phoi-gia-bao-nhieu-0")
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return handbooks.size
    }
}