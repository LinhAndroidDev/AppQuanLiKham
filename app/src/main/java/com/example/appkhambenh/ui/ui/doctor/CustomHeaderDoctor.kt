package com.example.appkhambenh.ui.ui.doctor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.HeaderDoctorBinding
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import com.example.appkhambenh.ui.utils.getActivity

class CustomHeaderDoctor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { HeaderDoctorBinding.inflate(LayoutInflater.from(context)) }

    init {
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(binding.root)
        val array =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomHeaderDoctor, 0, 0)
        array.apply {
            binding.titleView.text = getString(R.styleable.CustomHeaderDoctor_title_view_doctor)
            binding.titleNext.text = getString(R.styleable.CustomHeaderDoctor_title_next)
        }

        onClickView()
    }

    private fun onClickView() {
        binding.menu.setOnClickListener {
            val doctorActivity: DoctorActivity? = context.getActivity()
            doctorActivity?.openNavigationDrawer()
        }

        binding.avatar.setOnClickListener {
            createPopupWindow()
        }
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun createPopupWindow() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_setting_doctor, null)
        val width = LayoutParams.WRAP_CONTENT
        val height = LayoutParams.WRAP_CONTENT
        val popupWindow = PopupWindow(popupView, width, height, true)
        popupWindow.showAsDropDown(binding.avatar, 50, 0, Gravity.BOTTOM)

        popupView.findViewById<LinearLayout>(R.id.logoutDoctor).setOnClickListener {
            val activity: DoctorActivity? = context.getActivity()
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity?.startActivity(intent)
            SharePreferenceRepositoryImpl(context).saveCheckLogin(false)
        }

        popupView.findViewById<TextView>(R.id.nameAccount).text = SharePreferenceRepositoryImpl(context).getRollUser().let { roll ->
            when(roll) {
                1 -> {
                    "ADMIN"
                }

                2 -> {
                    "DOCTOR"
                }

                3 -> {
                    "NURSE"
                }

                4 -> {
                    "RECEPTIONIST"
                }

                5 -> {
                    "TECHNICIANS"
                }

                6 -> {
                    "RADIOLOGISTS"
                }

                else -> {
                    "RADIOLOGIST"
                }
            }
        }
    }
}