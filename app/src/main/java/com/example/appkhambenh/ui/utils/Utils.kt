package com.example.appkhambenh.ui.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.text.Spannable
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.FunctionMain
import com.example.appkhambenh.ui.ui.doctor.DoctorActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.contact.CallWithDoctorActivity
import com.example.appkhambenh.ui.ui.user.hospital.HospitalActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.Function
import com.example.appkhambenh.ui.ui.user.manage_appointment.ExaminationScheduleActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.example.appkhambenh.ui.ui.user.navigation.communication.FragmentCommunity
import com.example.appkhambenh.ui.ui.user.service.ExaminationServiceActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun View.expandView() {
    //Measure the view to get its target height
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    //Set initial height to 0 and make the view visible
    layoutParams .height = 0
    visibility = View.VISIBLE

    val anim = ValueAnimator.ofInt(0, targetHeight)
    anim.addUpdateListener { valueAnimator ->
        val value: Int = valueAnimator.animatedValue as Int
        layoutParams.height = value
        requestLayout()
    }
    anim.duration = 300L
    anim.start()
}

fun View.collapseView() {
    val targetHeight = 0

    val anim = ValueAnimator.ofInt(measuredHeight, targetHeight)
    anim.addUpdateListener { valueAnimator ->
        val value: Int = valueAnimator.animatedValue as Int
        layoutParams.height = value
        requestLayout()
    }
    anim.duration = 300L
    anim.interpolator = AccelerateDecelerateInterpolator()
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

        Function.HISTORY.id -> {
            val intent = Intent(activity, DoctorActivity::class.java)
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

fun View.rotationView(startCorner: Float, endCorner: Float) {
    val animator = ObjectAnimator.ofFloat(this, View.ROTATION, startCorner, endCorner)
    animator.duration = 300
    animator.start()
}

fun View.setBgColorViewTint(color: Int) {
    backgroundTintList = ColorStateList.valueOf(color)
}

fun View.rotationViewInfinite() {
    val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
    rotationAnimator.apply {
        duration = 2000
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        interpolator = LinearInterpolator()
        start()
    }
}

fun Fragment.addFragmentByTag(fragment: Fragment, changeId: Int, tag: String) {
    val fm = requireActivity().supportFragmentManager.beginTransaction()
    fm.hide(requireActivity().supportFragmentManager.findFragmentByTag(tag)!!)
        .add(changeId, fragment)
        .addToBackStack(null).commit()
}

fun Context.getDateFromCalendar(timeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            calendar.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat(DateUtils.DAY_OF_YEAR, Locale.getDefault())
            timeSelected.invoke(dateFormat.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun Spinner.createSpinner(context: Context, list: ArrayList<String>, selectItem: (Int) -> Unit) {
    val adapterSp = ArrayAdapter(context, R.layout.item_spinner, list)
    adapterSp.setDropDownViewResource(R.layout.spinner_dropdown_item)
    adapter = adapterSp

    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            selectItem.invoke(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
}
