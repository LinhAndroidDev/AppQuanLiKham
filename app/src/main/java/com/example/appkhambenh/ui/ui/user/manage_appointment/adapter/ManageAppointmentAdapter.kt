package com.example.appkhambenh.ui.ui.user.manage_appointment.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemManageAppointBinding
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.utils.ConvertUtils.dpToPx
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.squareup.picasso.Picasso

class ManageAppointmentAdapter(
    private var listRegisterChecking: ArrayList<RegisterChecking>?,
    val context: Context,
) : RecyclerView.Adapter<ManageAppointmentAdapter.ViewHolder>(), Filterable {

    val listRegisterCheckingOld by lazy { listRegisterChecking }
    var isClickEditAppoint: ((RegisterChecking?) -> Unit)? = null
    var isCancelAppoint: ((RegisterChecking?) -> Unit)? = null

    inner class ViewHolder(val v: ItemManageAppointBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ManageAppointmentAdapter.ViewHolder {
        val v by lazy {
            ItemManageAppointBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ManageAppointmentAdapter.ViewHolder, position: Int) {

        val activity by lazy { context as AppCompatActivity }
        val sharePrefer by lazy { SharePreferenceRepositoryImpl(context) }

        val register = listRegisterChecking?.get(position)
        sharePrefer.getUserAvatar().let {
            if (it.isNotEmpty()) {
                Picasso.get().load(it)
                    .placeholder(R.drawable.user_ad)
                    .error(R.drawable.user_ad)
                    .into(holder.v.avatarManage)
            }
        }
        holder.v.txtNamePatient.text = sharePrefer.getUserName()
        holder.v.txtTimeAppointment.text =
            register?.date + " " + context.getString(R.string.at) + " " + register?.hour
        holder.v.txtAddress.text = register?.department
        holder.v.txtService.text = register?.service
        holder.v.txtNameDoctor.text = register?.doctor
        holder.v.txtReasons.text = register?.reasons

        holder.itemView.setOnClickListener {
            if (holder.v.layoutExpand.height == 0) {
                holder.v.layoutExpand.visibility = View.VISIBLE
                expandView(holder.v.layoutExpand, 81.dpToPx(context))
                holder.v.txtExpand.text = activity.getString(R.string.collapse)
                val objectAnimator =
                    ObjectAnimator.ofFloat(holder.v.imgExpand, "rotation", 0f, 180f)
                objectAnimator.duration = 300L
                objectAnimator.start()
            } else {
                collapseView(holder.v.layoutExpand)
                holder.v.txtExpand.text = activity.getString(R.string.detail)
                val objectAnimator =
                    ObjectAnimator.ofFloat(holder.v.imgExpand, "rotation", 180f, 0f)
                objectAnimator.duration = 300L
                objectAnimator.start()
            }
        }

        holder.v.editAppoint.setOnClickListener {
            isClickEditAppoint?.invoke(register)
        }

        holder.v.llCancelAppointment.setOnClickListener {
            isCancelAppoint?.invoke(register)
        }
    }

    override fun getItemCount(): Int {
        return listRegisterChecking!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strSearch = p0.toString().lowercase().trim()
                listRegisterChecking = if (strSearch.isEmpty()) {
                    listRegisterCheckingOld
                } else {
                    val list = arrayListOf<RegisterChecking>()
                    for (registerChecking in listRegisterCheckingOld!!) {
                        if (
                            (registerChecking.date + registerChecking.hour + registerChecking.department + registerChecking.reasons)
                                .lowercase().trim().contains(strSearch)
                        ) {
                            list.add(registerChecking)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = listRegisterChecking

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    listRegisterChecking = p1.values as ArrayList<RegisterChecking>
                    notifyDataSetChanged()
                }
            }

        }
    }

    fun revertAppoint() {
        listRegisterChecking?.reverse()
        notifyDataSetChanged()
    }
}