package com.example.appkhambenh.ui.ui.user

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityLoginWithUserBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.ui.user.appointment.AppointmentActivity
import com.example.appkhambenh.ui.ui.user.qr.QrActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class LoginWithUser : BaseActivity<EmptyViewModel, ActivityLoginWithUserBinding>() {
    companion object {
        const val RESULT = "RESULT"
    }

    lateinit var bottomShareBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        val avatar = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "")
        if(avatar != ""){
            Picasso.get().load(avatar)
                .error(R.drawable.ad)
                .placeholder(R.drawable.loadimage)
                .into(binding.avartarUser)
        }else{
            binding.avartarUser.setImageResource(R.drawable.ad)
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityLoginWithUserBinding.inflate(inflater)

    private fun setStatusBar() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)

        val decorView = window.decorView //set status background black

        decorView.systemUiVisibility =
            decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
        //set status text  light
    }

    private fun initUi() {
        setStatusBar()

        binding.menuNav.setOnClickListener {
            binding.drawerView.openDrawer(GravityCompat.START)
        }

        binding.qrCode.setOnClickListener {
            val intent = Intent(this@LoginWithUser, QrActivity::class.java)
            startActivity(intent)
        }

        binding.appointment.setOnClickListener {
            val intent = Intent(this@LoginWithUser, AppointmentActivity::class.java)
            startActivity(intent)
        }

        binding.medicine.setOnClickListener {
            val intent = Intent(this@LoginWithUser, MedicineActivity::class.java)
            startActivity(intent)
        }

        /** Get Info User */
        binding.txtUserName.text =
            viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_NAME, "")
        binding.txtUserBirth.text =
            viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_BIRTH, "")

        /** User QR CODE */
        val result = intent.getStringExtra(RESULT)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        initNavigation()

        initBottomContact()
    }

    @SuppressLint("SetTextI18n")
    private fun initNavigation() {
        Picasso.get().load(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_AVATAR,"")
        ).placeholder(R.drawable.loadimage)
            .error(R.drawable.errorimage)
            .into(binding.avatarNav)

        binding.phoneNav.text = "Sđt: " + viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_PHONE,"")

        binding.emailNav.text = "Email: " + viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_EMAIL,"")

        binding.logout.setOnClickListener {
            val intent = Intent(this@LoginWithUser, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            viewModel.mPreferenceUtil.defaultPref()
                .edit().putBoolean(PreferenceKey.CHECK_LOGIN,false)
                .apply()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBottomContact() {

        binding.includeContact.layoutContact.setOnTouchListener { _, _ -> true }

        bottomShareBehavior = BottomSheetBehavior.from(binding.includeContact.layoutContact)
        binding.contact.setOnClickListener {
            bottomShareBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.changeIdUser.setOnTouchListener { _, even ->
            when (even?.actionMasked) {
                MotionEvent.ACTION_UP -> {
                    bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            true
        }

        binding.includeContact.callToDoctor.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel:" + "0969601767")
            startActivity(intent)
        }

        binding.includeContact.messageToDoctor.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.data = Uri.parse("sms:" + "0969601767")
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("WrongConstant")
    override fun onBackPressed() {
        if(bottomShareBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
            bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }else if(binding.drawerView.isDrawerOpen(Gravity.START)){
            binding.drawerView.close()
        } else{
            super.onBackPressed()
        }
    }
}