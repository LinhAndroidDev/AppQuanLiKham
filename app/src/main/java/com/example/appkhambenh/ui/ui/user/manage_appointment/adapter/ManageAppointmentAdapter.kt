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
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.utils.ConvertUtils.dpToPx
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ManageAppointmentAdapter(
    var listRegisterChecking: ArrayList<RegisterChecking>?,
    val context: Context,
) : RecyclerView.Adapter<ManageAppointmentAdapter.ViewHolder>(), Filterable {

    val listRegisterCheckingOld by lazy { listRegisterChecking }
    var isClickEditAppoint: ((RegisterChecking?) -> Unit)? = null
    var isCancelAppoint: ((RegisterChecking?) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: CircleImageView by lazy { itemView.findViewById(R.id.avatarManage) }
        val namePatient: TextView by lazy { itemView.findViewById(R.id.txt_name_patient) }
        val time: TextView by lazy { itemView.findViewById(R.id.txt_time_appointment) }
        val address: TextView by lazy { itemView.findViewById(R.id.txt_address) }
        val service: TextView by lazy { itemView.findViewById(R.id.txt_service) }
        val nameDoctor: TextView by lazy { itemView.findViewById(R.id.txt_name_doctor) }
        val reasons: TextView by lazy { itemView.findViewById(R.id.txt_reasons) }
        val txtExpand: TextView by lazy { itemView.findViewById(R.id.txtExpand) }
        val imgExpand: ImageView by lazy { itemView.findViewById(R.id.imgExpand) }
        val layoutExpand: LinearLayout by lazy { itemView.findViewById(R.id.layoutExpand) }
        val cancelAppoint: LinearLayout by lazy { itemView.findViewById(R.id.llCancelAppointment) }
        val editAppoint: LinearLayout by lazy { itemView.findViewById(R.id.editAppoint) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ManageAppointmentAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_manage_appoint, parent, false)
        return ViewHolder(itemView)
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
                    .into(holder.avatar)
            }
        }
        holder.namePatient.text = sharePrefer.getUserName()
        holder.time.text =
            register?.date + " " + context.getString(R.string.at) + " " + register?.hour
        holder.address.text = register?.department
        holder.service.text = register?.service
        holder.nameDoctor.text = register?.doctor
        holder.reasons.text = register?.reasons

        holder.itemView.setOnClickListener {
            if (holder.layoutExpand.height == 0) {
                holder.layoutExpand.visibility = View.VISIBLE
                expandView(holder.layoutExpand, 81.dpToPx(context))
                holder.txtExpand.text = activity.getString(R.string.collapse)
                val objectAnimator = ObjectAnimator.ofFloat(holder.imgExpand, "rotation", 0f, 180f)
                objectAnimator.duration = 300L
                objectAnimator.start()
            } else {
                collapseView(holder.layoutExpand)
                holder.txtExpand.text = activity.getString(R.string.detail)
                val objectAnimator = ObjectAnimator.ofFloat(holder.imgExpand, "rotation", 180f, 0f)
                objectAnimator.duration = 300L
                objectAnimator.start()
            }
        }

        holder.editAppoint.setOnClickListener {
            isClickEditAppoint?.invoke(register)
        }

        holder.cancelAppoint.setOnClickListener {
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