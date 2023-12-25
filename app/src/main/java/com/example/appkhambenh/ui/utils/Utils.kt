package com.example.appkhambenh.ui.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.ui.model.DepartmentClinic
import com.example.appkhambenh.ui.model.Doctor
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.DepartmentAdapter
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.DoctorAdapter
import com.example.appkhambenh.ui.ui.user.csyt.CsytActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.Function
import com.example.appkhambenh.ui.ui.user.manage_appointment.ManageAppointmentActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


var getNameDepartment: ((String) -> Unit)? = null
var getNameDoctor: ((String) -> Unit)? = null

fun getDataDepartment(
    context: Context,
    rcvDepartment: RecyclerView,
) {
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    databaseReference.child("Department")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listDepartment = ArrayList<DepartmentClinic>()
                for (dataSnapshot in snapshot.children) {
                    val department: DepartmentClinic? = dataSnapshot.getValue(
                        DepartmentClinic::class.java
                    )
                    listDepartment.add(department!!)
                }
                val departmentAdapter = DepartmentAdapter(listDepartment, context)
                departmentAdapter.onSelectDepartment = {
                    getNameDepartment?.invoke(it)
                }
                val linear =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rcvDepartment.layoutManager = linear
                rcvDepartment.adapter = departmentAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
}

fun getDataDoctor(
    context: Context,
    rcvDoctor: RecyclerView,
) {
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    databaseReference.child("Doctor")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listDoctor = ArrayList<Doctor>()
                for (dataSnapshot in snapshot.children) {
                    val doctor: Doctor? = dataSnapshot.getValue(
                        Doctor::class.java
                    )
                    listDoctor.add(doctor!!)
                }
                val departmentAdapter = DoctorAdapter(listDoctor, context)
                departmentAdapter.selectDoctor = {
                    getNameDoctor?.invoke(it)
                }
                val linear =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rcvDoctor.layoutManager = linear
                rcvDoctor.adapter = departmentAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
}

fun expandView(view: View, height: Int) {
    view.measure(
        View.MeasureSpec.makeMeasureSpec(
            (view.parent as View).width,
            View.MeasureSpec.EXACTLY
        ),
        View.MeasureSpec.makeMeasureSpec(1024, View.MeasureSpec.AT_MOST)
    )
    val anim = ValueAnimator.ofInt(view.height, height)
    anim.addUpdateListener { valueAnimator ->
        val value: Int = valueAnimator.animatedValue as Int
        view.layoutParams.height = value
        view.requestLayout()
    }
    anim.duration = 300L
    anim.start()
}

fun collapseView(view: View) {
    val targetHeight = 0

    val anim = ValueAnimator.ofInt(view.height, targetHeight)
    anim.addUpdateListener { valueAnimator ->
        val value: Int = valueAnimator.animatedValue as Int
        view.layoutParams.height = value
        view.requestLayout()
    }
    anim.duration = 300L
    anim.start()
}

fun timeEffectView(view: View): Long {
    return (view.measuredHeight / view.context.resources.displayMetrics.density).toLong()
}

fun validateEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()
}

fun validatePassword(password: String): Boolean {
    return !TextUtils.isEmpty(password) && password.length >= 7
}

fun validatePhone(phone: String): Boolean {
    return !TextUtils.isEmpty(phone) && (Patterns.PHONE.matcher(phone)
        .matches() && phone.length >= 10)
}

fun setTextNotification(reasons: String, date: String, hour: String): SpannableString {
    val notification =
        "Lịch hẹn $date lúc $hour lý do $reasons của bạn chưa được xác nhận. Chúng tôi đang cố gắng khắc phục sự cố"
    val spannable = SpannableString(notification)

    val dateStartIndex = notification.indexOf(date)
    if (dateStartIndex != -1) {
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            dateStartIndex,
            dateStartIndex + date.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    setStyleTextAtPosition(notification, hour, ForegroundColorSpan(Color.BLUE), spannable)

    setStyleTextAtPosition(notification, reasons, StyleSpan(Typeface.BOLD_ITALIC), spannable)

    return spannable
}

fun setStyleTextAtPosition(str: String, strChange: String, style: Any, spannable: Spannable) {
    val strRegex = Regex(strChange)
    val strMatchResult = strRegex.find(str)
    if (strMatchResult != null) {
        val strStartIndex = strMatchResult.range.first
        spannable.setSpan(
            style,
            strStartIndex,
            strStartIndex + strChange.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun onClickFunction(index: Int, activity: FragmentActivity) {
    when (index) {
        Function.BOOK_DOCTOR.id -> {
            val intent = Intent(activity, SearchDoctorActivity::class.java)
            activity.startActivity(intent)
        }

        Function.BOOK_CAPITAL.id -> {
            val intent = Intent(activity, CsytActivity::class.java)
            activity.startActivity(intent)
        }

        Function.SCHEDULE.id -> {
            val intent = Intent(activity, ManageAppointmentActivity::class.java)
            activity.startActivity(intent)
        }

        Function.MEDICINE.id -> {
            val intent = Intent(activity, MedicineActivity::class.java)
            activity.startActivity(intent)
        }
    }
}

fun animRotation45(img: ImageView) {
    val animator = ObjectAnimator.ofFloat(img, View.ROTATION, 0f, 45f)
    animator.duration = 300
    animator.start()
}

fun animRotationBack0(img: ImageView) {
    val animator = ObjectAnimator.ofFloat(img, View.ROTATION, 45f, 0f)
    animator.duration = 300
    animator.start()
}
