package com.example.appkhambenh.ui.ui.doctor.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemFunctionDoctorBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.utils.ConvertUtils.widthDevice

class FunctionDoctor(
    val title: String,
    val icon: Int,
    val color: Int,
    val quantity: Int,
)

class FunctionDoctorAdapter(private val context: Context) :
    BaseAdapter<FunctionDoctor, ItemFunctionDoctorBinding>() {

    override fun getLayout(): Int = R.layout.item_function_doctor

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemFunctionDoctorBinding>,
        position: Int,
    ) {
        val activity = context as Activity
        val function = items[position]
        holder.v.root.layoutParams = ViewGroup.LayoutParams(
            activity.widthDevice()/2,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        holder.v.apply {
            title.text = function.title
            icon.setImageResource(function.icon)
            icon.setColorFilter(context.getColor(function.color))
            tvQuantity.text = function.quantity.toString()
            tvQuantity.setTextColor(context.getColor(function.color))
            viewCover.isVisible = position % 2 == 1
        }
    }
}