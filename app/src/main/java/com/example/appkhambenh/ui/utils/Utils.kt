package com.example.appkhambenh.ui.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.text.Spannable
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.FunctionMain
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.contact.CallWithDoctorActivity
import com.example.appkhambenh.ui.ui.user.hospital.HospitalActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.Function
import com.example.appkhambenh.ui.ui.user.manage_appointment.ExaminationScheduleActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.example.appkhambenh.ui.ui.user.navigation.communication.FragmentCommunity
import com.example.appkhambenh.ui.ui.user.service.ExaminationServiceActivity

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

fun validateEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()
}

fun validatePassword(password: String): Boolean {
    return !TextUtils.isEmpty(password) && password.length >= 6
}

fun validatePhone(phone: String): Boolean {
    return !TextUtils.isEmpty(phone) && (Patterns.PHONE.matcher(phone)
        .matches() && phone.length >= 10)
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

fun functionHome(context: Context): ArrayList<FunctionMain> {
    val function = arrayListOf<FunctionMain>()
    function.add(FunctionMain(R.drawable.icon_doctor, context.getString(R.string.appoint_doctor)))
    function.add(FunctionMain(R.drawable.ic_action_schedule, context.getString(R.string.examination_schedule)))
    function.add(FunctionMain(R.drawable.ic_action_department, context.getString(R.string.hospital_examination)))
    function.add(FunctionMain(R.drawable.ic_action_message, context.getString(R.string.community)))
    function.add(FunctionMain(R.drawable.ic_action_service, context.getString(R.string.check_service)))
    function.add(FunctionMain(R.drawable.ic_action_history, context.getString(R.string.medical_examination_history)))
    function.add(FunctionMain(R.drawable.ic_action_contact, context.getString(R.string.contact)))
    function.add(FunctionMain(R.drawable.ic_action_medicine, context.getString(R.string.drug_information)))

    return function
}

fun onClickFunction(index: Int, activity: FragmentActivity) {
    when (index) {
        Function.BOOK_DOCTOR.id -> {
            val intent = Intent(activity, SearchDoctorActivity::class.java)
            activity.startActivity(intent)
        }

        Function.BOOK_CAPITAL.id -> {
            val intent = Intent(activity, HospitalActivity::class.java)
            activity.startActivity(intent)
        }

        Function.SCHEDULE.id -> {
            val intent = Intent(activity, ExaminationScheduleActivity::class.java)
            activity.startActivity(intent)
        }

        Function.MEDICINE.id -> {
            val intent = Intent(activity, MedicineActivity::class.java)
            activity.startActivity(intent)
        }

        Function.COMMUNITY.id -> {
            (activity as HomeActivity).replaceFragment(FragmentCommunity(), R.id.changeIdHome)
        }

        Function.BOOK_SERVICE.id -> {
            val intent = Intent(activity, ExaminationServiceActivity::class.java)
            activity.startActivity(intent)
        }

        Function.CONTACT.id -> {
            val intent = Intent(activity, CallWithDoctorActivity::class.java)
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

fun setBgColorViewTint(v: View, color: Int) {
    v.backgroundTintList = ColorStateList.valueOf(color)
}
