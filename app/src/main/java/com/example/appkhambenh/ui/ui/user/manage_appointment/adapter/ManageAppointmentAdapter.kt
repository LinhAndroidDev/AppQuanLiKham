package com.example.appkhambenh.ui.ui.user.manage_appointment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ManageAppointmentAdapter(
    private val listRegisterChecking: ArrayList<RegisterChecking>?,
    val context: Context
) : RecyclerView.Adapter<ManageAppointmentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val avatar: CircleImageView = itemView.findViewById(R.id.avatarManage)
        val cancelAppointment: LinearLayout = itemView.findViewById(R.id.llCancelAppointment)
        val namePatient: TextView = itemView.findViewById(R.id.txt_name_patient)
        val time: TextView = itemView.findViewById(R.id.txt_time_appointment)
        val address: TextView = itemView.findViewById(R.id.txt_address)
        val service: TextView = itemView.findViewById(R.id.txt_service)
        val nameDoctor: TextView = itemView.findViewById(R.id.txt_name_doctor)
        val reasons: TextView = itemView.findViewById(R.id.txt_reasons)
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
    }

    override fun getItemCount(): Int {
        return listRegisterChecking!!.size
    }
}