package com.example.appkhambenh.ui.ui.user

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityLoginWithUserBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.FunctionNavigation
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.ui.user.appointment.AppointmentActivity
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.manage_appointment.ManageAppointmentActivity
import com.example.appkhambenh.ui.ui.user.qr.QrActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.example.appkhambenh.ui.ui.user.navigation.information.InformationActivity
import com.example.appkhambenh.ui.ui.user.navigation.adapter.FunctionNavigationAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@Suppress("DEPRECATION")
class LoginWithUser : BaseActivity<LoginWithUserViewModel, ActivityLoginWithUserBinding>() {
    companion object {
        const val RESULT = "RESULT"
    }

    lateinit var bottomShareBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("SetTextI18n")
    override fun bindData() {
        super.bindData()

        val loading = ProgressDialog(this)
        loading.setMessage("Please wait...")
        loading.setTitle("Thông báo")
        loading.setCancelable(false)
        viewModel.loadingLiveData.observe(this, Observer {
            if (it) {
                loading.show()
            } else {
                loading.dismiss()
            }
        })

        val userId = viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_ID, 0).toString()
        val requestUserId: RequestBody =
            userId.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        viewModel.getUserInfo(requestUserId)

        viewModel.nameLiveData.observe(this, Observer{
            binding.txtUserName.text = it
        })

        viewModel.emailLiveData.observe(this, Observer{
            binding.layoutNavigation.emailNav.text = it
        })

        viewModel.birthLiveData.observe(this, Observer{
            binding.txtUserBirth.text = it
        })

        viewModel.avatarLiveData.observe(this, Observer{
            if(it.isNotEmpty()){
                Picasso.get().load(it)
                    .error(R.drawable.user_ad)
                    .placeholder(R.drawable.user_ad)
                    .into(binding.avartarUser)

                Picasso.get().load(it)
                    .placeholder(R.drawable.user_ad)
                    .error(R.drawable.user_ad)
                    .into(binding.layoutNavigation.avatarNav)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initNavigation() {

        binding.layoutNavigation.phoneNav.text = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_PHONE, "")

        binding.layoutNavigation.logout.setOnClickListener {
            val intent = Intent(this@LoginWithUser, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            viewModel.mPreferenceUtil.defaultPref()
                .edit().putBoolean(PreferenceKey.CHECK_LOGIN, false)
                .apply()
        }

        binding.layoutNavigation.avatarNav.setOnClickListener{
            val intent = Intent(this@LoginWithUser, SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        val listFunction: ArrayList<FunctionNavigation> = arrayListOf()
        listFunction.add(FunctionNavigation(R.drawable.ic_action_information, "Tài Khoản"))
        listFunction.add(FunctionNavigation(R.drawable.ic_action_reset_password, "Đổi Mật Khẩu"))
        listFunction.add(FunctionNavigation(R.drawable.ic_action_notification, "Thông Báo"))

        val functionAdapter = FunctionNavigationAdapter(listFunction)
        binding.layoutNavigation.rcvFunctionNavigation.apply {
            adapter = functionAdapter
        }

        functionAdapter.onClickItem = { position->
            when(position){
                0 ->{
                    val intent = Intent(this@LoginWithUser, InformationActivity::class.java)
                    startActivity(intent)
                }
                1 ->{

                }
                2 ->{

                }
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityLoginWithUserBinding.inflate(inflater)

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

    @SuppressLint("IntentReset")
    private fun initUi() {
        setStatusBar()

        binding.avartarUser.setOnClickListener{
            val intent = Intent(this@LoginWithUser, SeeAvatarActivity::class.java)
            startActivity(intent)
        }

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

        binding.cvManageAppointment.setOnClickListener {
            val intent = Intent(this@LoginWithUser, ManageAppointmentActivity::class.java)
            startActivity(intent)
        }

        binding.layoutNavigation.cirImgEditAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "images/"
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            startActivityForResult(intent, 100)
        }

        /** User QR CODE */
        val result = intent.getStringExtra(RESULT)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        initNavigation()

        initBottomContact()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val intent = Intent(this@LoginWithUser, EditAvatarActivity::class.java)
            intent.putExtra("uri_avatar", data?.data.toString())
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("WrongConstant")
    override fun onBackPressed() {
        if (bottomShareBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else if (binding.drawerView.isDrawerOpen(Gravity.START)) {
            binding.drawerView.close()
        } else {
            super.onBackPressed()
        }
    }
}