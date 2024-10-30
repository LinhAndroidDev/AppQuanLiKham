package com.example.appkhambenh.ui.ui.user.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemFunctionHomeBinding
import com.example.appkhambenh.databinding.ItemMedicalHandbookBinding
import com.example.appkhambenh.databinding.ItemSlideBinding
import com.example.appkhambenh.databinding.ItemTopCsytBinding
import com.example.appkhambenh.databinding.LayoutHighlightDoctorBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import com.example.appkhambenh.ui.model.Doctor
import com.example.appkhambenh.ui.model.FunctionMain
import com.example.appkhambenh.ui.model.MedicalHandbook
import com.squareup.picasso.Picasso

class FunctionHomeAdapter : BaseAdapter<FunctionMain, ItemFunctionHomeBinding>() {
    var onClickItem: ((Int) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_function_home

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemFunctionHomeBinding>, position: Int) {
        val function = items[position]
        holder.v.imgFunction.setImageResource(function.image)
        holder.v.nameFunction.text = function.name

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(position)
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }
}

class DoctorHighlightAdapter : BaseAdapter<Doctor, LayoutHighlightDoctorBinding>() {
    var onClickItem: ((Boolean) -> Unit)? = null

    override fun getLayout(): Int = R.layout.layout_highlight_doctor

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onBindViewHolder(
        holder: BaseViewHolder<LayoutHighlightDoctorBinding>,
        position: Int,
    ) {
        holder.v.apply {
            imgHighlightDoctor.setImageResource(items[position].image!!)
            nameHighlight.text = "${items[position].position} ${items[position].name}"
            specialistHighlight.text = items[position].specialist
            hospitalHighlight.text = items[position].hospital
        }

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(true)
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }
}

class ImageAdapter(private val context: Context) : BaseAdapter<Int, ItemSlideBinding>() {
    var onClickItem: ((Boolean) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_slide

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemSlideBinding>, position: Int) {
        holder.v.slide.setImageResource(items[position])
        holder.v.nameSlide.text = when (position) {
            0 -> context.getString(R.string.doctor_has_experience)
            1 -> context.getString(R.string.priority_to_check)
            2 -> context.getString(R.string.book_online)
            3 -> context.getString(R.string.doctor_has_experience)
            else -> context.getString(R.string.priority_to_check)
        }

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onClickItem?.invoke(true)
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }
}

class MedicalHandBookAdapter(private val context: Context) :
    BaseAdapter<MedicalHandbook, ItemMedicalHandbookBinding>() {
    var url: ((String) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_medical_handbook
    

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemMedicalHandbookBinding>,
        position: Int,
    ) {
        Picasso.get().load(items[position].image)
            .resize(
                context.resources.getDimension(R.dimen.dimen_120).toInt(),
                context.resources.getDimension(R.dimen.dimen_80).toInt()
            )
            .centerCrop().into(holder.v.imgHandbook)
        holder.v.apply {
            titleHandbook.text = items[position].title
            timeHandbook.text = items[position].time
            minutesHandbook.text = items[position].minutes
        }

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
}

class TopCsytAdapter : BaseAdapter<Int, ItemTopCsytBinding>() {
    var onCLickItem: (() -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_top_csyt
    
    override fun getItemCount(): Int = 10

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemTopCsytBinding>, position: Int) {
        when (position) {
            0 -> holder.v.viewStartTopCsyt.visibility = View.VISIBLE
            else -> holder.v.viewStartTopCsyt.visibility = View.GONE
        }

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.alpha = 0.3f
                }

                MotionEvent.ACTION_UP -> {
                    holder.itemView.alpha = 1f
                    onCLickItem?.invoke()
                }

                MotionEvent.ACTION_CANCEL -> {
                    holder.itemView.alpha = 1f
                }
            }
            true
        }
    }
}