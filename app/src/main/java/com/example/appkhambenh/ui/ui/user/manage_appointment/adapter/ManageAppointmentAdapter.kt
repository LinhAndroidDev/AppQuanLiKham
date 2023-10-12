package com.example.appkhambenh.ui.ui.user.manage_appointment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.example.appkhambenh.ui.utils.timeEffectView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ManageAppointmentAdapter(
    var listRegisterChecking: ArrayList<RegisterChecking>?,
    val context: Context
) : RecyclerView.Adapter<ManageAppointmentAdapter.ViewHolder>(), Filterable {

    var listRegisterCheckingOld: ArrayList<RegisterChecking>? = listRegisterChecking
    var isClickEditAppoint: ((RegisterChecking?) -> Unit)? = null
    var isCancelAppoint: ((RegisterChecking?) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val avatar: CircleImageView = itemView.findViewById(R.id.avatarManage)
        val namePatient: TextView = itemView.findViewById(R.id.txt_name_patient)
        val time: TextView = itemView.findViewById(R.id.txt_time_appointment)
        val address: TextView = itemView.findViewById(R.id.txt_address)
        val service: TextView = itemView.findViewById(R.id.txt_service)
        val nameDoctor: TextView = itemView.findViewById(R.id.txt_name_doctor)
        val reasons: TextView = itemView.findViewById(R.id.txt_reasons)
        val txtExpand: TextView = itemView.findViewById(R.id.txtExpand)
        val imgExpand: ImageView = itemView.findViewById(R.id.imgExpand)
        val layoutExpand: LinearLayout = itemView.findViewById(R.id.layoutExpand)
        val cancelAppoint: LinearLayout = itemView.findViewById(R.id.llCancelAppointment)
        val editAppoint: LinearLayout = itemView.findViewById(R.id.editAppoint)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ManageAppointmentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_manage_appoint, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ManageAppointmentAdapter.ViewHolder, position: Int) {

        val activity = context as AppCompatActivity
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

        val register = listRegisterChecking?.get(position)
        val strAvatar: String = sharedPreferences.getString(PreferenceKey.USER_AVATAR, "").toString()
        if(strAvatar.isNotEmpty()){
            Picasso.get().load(strAvatar)
                .placeholder(R.drawable.user_ad)
                .error(R.drawable.user_ad)
                .into(holder.avatar)
        }
        holder.namePatient.text = sharedPreferences.getString(PreferenceKey.USER_NAME, "").toString()
        holder.time.text = register?.date + " " + context.getString(R.string.at) + " " + register?.hour
        holder.address.text = register?.department
        holder.service.text = register?.service
        holder.nameDoctor.text = register?.doctor
        holder.reasons.text = register?.reasons

        holder.itemView.setOnClickListener {
            if(holder.layoutExpand.visibility == View.GONE){
                expandView(holder.layoutExpand)
                holder.txtExpand.text = "Thu gọn"
                holder.imgExpand
                    .animate()
                    .rotationBy(180f)
                    .duration = timeEffectView(holder.layoutExpand)
            } else{
                collapseView(holder.layoutExpand)
                holder.txtExpand.text = "Chi tiết"
                holder.imgExpand
                    .animate()
                    .rotationBy(-180f)
                    .duration = timeEffectView(holder.layoutExpand)
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
                listRegisterChecking = if(strSearch.isEmpty()){
                    listRegisterCheckingOld
                }else{
                    val list = arrayListOf<RegisterChecking>()
                    for(registerChecking in listRegisterCheckingOld!!){
                        if(
                            (registerChecking.date + registerChecking.hour + registerChecking.department)
                                .lowercase().trim().contains(strSearch)
                        ){
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
                if(p1?.values != null){
                    listRegisterChecking = p1.values as ArrayList<RegisterChecking>
                    notifyDataSetChanged()
                }
            }

        }
    }
}