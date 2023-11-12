package com.example.appkhambenh.ui.ui.user

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentLoginWithUserBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.csyt.ConnectCsytActivity
import com.example.appkhambenh.ui.ui.doctor.statistical.StatisticalActivity
import com.example.appkhambenh.ui.ui.doctor.time_working.EditTimeWorkActivity
import com.example.appkhambenh.ui.ui.user.appointment.AppointmentActivity
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.manage_appointment.ManageAppointmentActivity
import com.example.appkhambenh.ui.ui.user.medicine.MedicineActivity
import com.example.appkhambenh.ui.ui.user.qr.QrActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class FragmentLoginWithUser : BaseFragment<LoginWithUserViewModel, FragmentLoginWithUserBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("SetTextI18n")
    override fun bindData() {
        super.bindData()

        val loading = ProgressDialog(requireActivity())
        loading.setMessage("Please wait...")
        loading.setTitle("Thông báo")
        loading.setCancelable(false)
        viewModel.loadingLiveData.observe(this){
            if (it) {
                loading.show()
            } else {
                object : CountDownTimer(500, 500){
                    override fun onTick(p0: Long) {}

                    override fun onFinish() {
                        loading.dismiss()
                    }

                }.start()
            }
        }

        val userId = viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_ID, 0).toString()
        val requestUserId: RequestBody =
            userId.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        viewModel.getUserInfo(requestUserId)

        viewModel.userLiveData.observe(this) {
            if(it.result?.type != 0){
                if(it.result?.type == 2) {
                    binding.noteDoctor.text = "Cơ sở y tế: "
                }
                binding.noteDoctor.visibility = View.VISIBLE
                binding.functionAccessoryDoctor.visibility = View.VISIBLE
                binding.functionMainDoctor.visibility = View.VISIBLE
                binding.functionAccessoryPatients.visibility = View.GONE
                binding.functionMainPatients.visibility = View.GONE
            }

            binding.txtUserBirth.text = if(it.result?.type != 2) it.result?.birth else it.result.address
            binding.txtUserName.text = it.result?.name

            if (it.result?.avatar!!.isNotEmpty()) {
                Picasso.get().load(it.result.avatar)
                    .error(R.drawable.user_ad)
                    .placeholder(R.drawable.user_ad)
                    .into(binding.avartarUser)
            }
        }
    }

    @SuppressLint("IntentReset")
    private fun initUi() {

        binding.avartarUser.setOnClickListener{
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.qrCode.setOnClickListener {
            val intent = Intent(requireActivity(), QrActivity::class.java)
            startActivity(intent)
        }

        binding.appointment.setOnClickListener {
            val intent = Intent(requireActivity(), AppointmentActivity::class.java)
            startActivity(intent)
        }

        binding.medicine.setOnClickListener {
            val intent = Intent(requireActivity(), MedicineActivity::class.java)
            startActivity(intent)
        }

        binding.cvManageAppointment.setOnClickListener {
            val intent = Intent(requireActivity(), ManageAppointmentActivity::class.java)
            startActivity(intent)
        }

        binding.addAppoint.setOnClickListener {
            val intent = Intent(requireActivity(), EditTimeWorkActivity::class.java)
            startActivity(intent)
        }

        binding.cvStatisticalAppoint.setOnClickListener {
            val intent = Intent(requireActivity(), StatisticalActivity::class.java)
            startActivity(intent)
        }

        binding.cvHistoryMedicalExam.setOnClickListener {
            val intent = Intent(requireActivity(), ConnectCsytActivity::class.java)
            startActivity(intent)
        }

        /** User QR CODE */
        val result = activity?.intent?.getStringExtra(LoginWithUser.RESULT)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                Toast.makeText(requireActivity(), result.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            val intent = Intent(requireActivity(), EditAvatarActivity::class.java)
            intent.putExtra("uri_avatar", data?.data.toString())
            startActivity(intent)
        }
    }
    
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentLoginWithUserBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }

}