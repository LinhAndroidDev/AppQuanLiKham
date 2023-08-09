package com.example.appkhambenh.ui.ui.doctor.department.adapter

import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.DepartmentClinic
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.ArrayList

class EditDepartmentAdapter(
    private val listDepartment: ArrayList<DepartmentClinic>,
    val context: Context,
) : RecyclerView.Adapter<EditDepartmentAdapter.ViewHolder>() {
    var openDialogEditDepartment: ((DepartmentClinic) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtNameDepartment)
        val menu: LinearLayout = itemView.findViewById(R.id.menu)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): EditDepartmentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_edit_department, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EditDepartmentAdapter.ViewHolder, position: Int) {
        val department: DepartmentClinic = listDepartment[position]
        holder.txtName.text = department.nameDpt
        holder.menu.setOnClickListener {
            val popUpView: View = View.inflate(context, R.layout.popup_edit_department, null)
            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val focusable = true
            val popupWindow = PopupWindow(popUpView, width, height, focusable)
            popupWindow.showAsDropDown(holder.menu, 0, -50, Gravity.BOTTOM)

            val txtEditDepartment: LinearLayout = popUpView.findViewById(R.id.txtEditDepartment)
            val txtDeleteDepartment: LinearLayout = popUpView.findViewById(R.id.txtDeleteDepartment)
            val databaseReference = FirebaseDatabase.getInstance().reference

            txtDeleteDepartment.setOnClickListener {
                databaseReference.child("Department")
                    .child(department.time.toString())
                    .removeValue()
                popupWindow.dismiss()
                Toast.makeText(context, "Bạn đã xoá thành công khoa ${department.nameDpt}", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }

            txtEditDepartment.setOnClickListener {
                popupWindow.dismiss()
                openDialogEditDepartment?.invoke(department)
            }
        }
    }

    override fun getItemCount(): Int {
        return listDepartment.size
    }
}