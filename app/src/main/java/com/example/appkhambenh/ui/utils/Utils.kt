package com.example.appkhambenh.ui.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.FunctionMain
import com.example.appkhambenh.ui.ui.doctor.DoctorActivity
import com.example.appkhambenh.ui.ui.doctor.adapter.CustomArrayAdapter
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.contact.CallWithDoctorActivity
import com.example.appkhambenh.ui.ui.user.hospital.HospitalActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.FragmentHome
import com.example.appkhambenh.ui.ui.user.home.Function
import com.example.appkhambenh.ui.ui.user.manage_appointment.ExaminationScheduleActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.example.appkhambenh.ui.ui.user.navigation.communication.FragmentCommunity
import com.example.appkhambenh.ui.ui.user.service.ExaminationServiceActivity
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Expand View Smooth With 300L
 */
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

/**
 * Collapse View Smooth With 300L
 */
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

/**
 * Create List Service For Appointment Schedule Screen
 */
fun FragmentHome.functionHome(): ArrayList<FunctionMain> {
    val function = arrayListOf<FunctionMain>()
    function.add(FunctionMain(R.drawable.icon_doctor, getString(R.string.appoint_doctor)))
    function.add(FunctionMain(R.drawable.ic_action_schedule, getString(R.string.examination_schedule)))
    function.add(FunctionMain(R.drawable.ic_action_department, getString(R.string.hospital_examination)))
    function.add(FunctionMain(R.drawable.ic_action_message, getString(R.string.community)))
    function.add(FunctionMain(R.drawable.ic_action_service, getString(R.string.check_service)))
    function.add(FunctionMain(R.drawable.ic_action_history, getString(R.string.medical_examination_history)))
    function.add(FunctionMain(R.drawable.ic_action_contact, getString(R.string.contact)))
    function.add(FunctionMain(R.drawable.ic_action_medicine, getString(R.string.drug_information)))

    return function
}

/**
 * This Function Use To Handle Event Click Into Item In List Service Of Appointment Schedule Screen
 */
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

/**
 * This Animator Use To Rotate View 0 Degrees To 45 Degrees
 */
fun animRotation45(img: ImageView) {
    val animator = ObjectAnimator.ofFloat(img, View.ROTATION, 0f, 45f)
    animator.duration = 300
    animator.start()
}

/**
 * This Animator Use To Rotate View 45 Degrees To 0 Degrees
 */
fun animRotationBack0(img: ImageView) {
    val animator = ObjectAnimator.ofFloat(img, View.ROTATION, 45f, 0f)
    animator.duration = 300
    animator.start()
}

/**
 * This Animator Use To Rotation View With Any Corner
 */
fun View.rotationView(startCorner: Float, endCorner: Float) {
    val animator = ObjectAnimator.ofFloat(this, View.ROTATION, startCorner, endCorner)
    animator.duration = 300
    animator.start()
}

/**
 * Change Color Back Ground Tint Of View
 */
fun View.setBgColorViewTint(color: Int) {
    backgroundTintList = ColorStateList.valueOf(context.getColor(color))
}

/**
 * This Animator Use For Show Loading When Has Data
 */
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

/**
 * Add Fragment By Tag And Check If Fragment Which Find By Tag Exist, If Exist Hide Fragment
 */
fun Fragment.addFragmentByTag(fragment: Fragment, changeId: Int, tag: String) {
    val fmToHide = parentFragmentManager.findFragmentByTag(tag)
    val fm = parentFragmentManager.beginTransaction()
    if (fmToHide != null) {
        fm.hide(fmToHide)
    }
    fm.add(changeId, fragment, tag)
        .addToBackStack(null).commit()
}

/**
 * Create Calendar And Get Date By (timeSelected: (String) -> Unit)
 */
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

/**
 * Create Spinner And Get Position Selected By (selectItem: (Int) -> Unit)
 */
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

/**
 * This Function To Get Current Activity And Check If Activity Nullable
 */
inline fun <reified T : Activity> Context.getActivity(): T? {
    var context = this
    while (context is ContextWrapper) {
        if (context is T) {
            return context
        }
        context = context.baseContext
    }
    return null
}

@SuppressLint("DiscouragedPrivateApi")
fun AutoCompleteTextView.initTextComplete(activity: Activity, data: List<String?>) {
    val adapter = CustomArrayAdapter(activity, android.R.layout.simple_dropdown_item_1line, data)
    setAdapter(adapter)
    if(data.size > 5) dropDownHeight = 500
    post {
        try {
            val popupField: Field = AutoCompleteTextView::class.java.getDeclaredField("mPopup")
            popupField.isAccessible = true
            val popup: ListPopupWindow = popupField.get(this) as ListPopupWindow
            popup.width = this.width
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Hiển thị danh sách khi AutoCompleteTextView nhận được focus
    setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            showDropDown()
        }
    }

    // Hiển thị danh sách khi AutoCompleteTextView được click
    setOnClickListener {
        showDropDown()
    }
}
