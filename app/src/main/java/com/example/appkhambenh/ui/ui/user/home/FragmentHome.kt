package com.example.appkhambenh.ui.ui.user.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentHomeBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.FunctionMain
import com.example.appkhambenh.ui.ui.doctor.statistical.StatisticalActivity
import com.example.appkhambenh.ui.ui.doctor.time_working.EditTimeWorkActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.appointment.AppointmentActivity
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.home.adapter.FunctionHomeAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.ImageAdapter
import com.example.appkhambenh.ui.ui.user.qr.QrActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import com.example.appkhambenh.ui.utils.onClickFunction
import java.util.*
import kotlin.math.abs

@Suppress("DEPRECATION")
class FragmentHome : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val timer = Timer()

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
        viewModel.getUserInfo( convertToRequestBody(userId) )

        viewModel.userLiveData.observe(this) {
            if(it.result?.type != 0){
                if(it.result?.type == 2) {
                    binding.noteDoctor.text = "Cơ sở y tế: "
                }
                binding.noteDoctor.visibility = View.VISIBLE
                binding.functionAccessoryDoctor.visibility = View.VISIBLE
                binding.layoutDoctor.visibility = View.VISIBLE
                binding.functionAccessoryPatients.visibility = View.GONE
                binding.layoutPatient.visibility = View.GONE
                binding.rcvFunctionMain.visibility = View.GONE
                (activity as HomeActivity).hideIconBook()
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

        function()

        scrollInfinity()

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

        binding.addAppoint.setOnClickListener {
            val intent = Intent(requireActivity(), EditTimeWorkActivity::class.java)
            startActivity(intent)
        }

        binding.cvStatisticalAppoint.setOnClickListener {
            val intent = Intent(requireActivity(), StatisticalActivity::class.java)
            startActivity(intent)
        }

        /** User QR CODE */
        val result = activity?.intent?.getStringExtra(HomeActivity.RESULT)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                Toast.makeText(requireActivity(), result.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        
    }

    private fun scrollInfinity(){

        setUpTransformer()

        val images = arrayListOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3)
        images.add(R.drawable.slide1)
        images.add(0, R.drawable.slide3)
        val slideAdapter = ImageAdapter(images)
        binding.slide.adapter = slideAdapter

        val recyclerView = binding.slide.getChildAt(0) as RecyclerView
        recyclerView.scrollToPosition(1)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val itemCount = binding.slide.adapter?.itemCount ?: 0

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
                val lastItemVisible = layoutManager.findLastVisibleItemPosition()

                if (firstItemVisible == (itemCount - 1) && dx > 0) {
                    recyclerView.scrollToPosition(1)
                } else if (lastItemVisible == 0 && dx < 0) {
                    recyclerView.scrollToPosition(itemCount - 2)
                }
            }
        })

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                binding.slide.currentItem = binding.slide.currentItem + 1
            }
        }, 0, 3000)
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(5))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.18f
        }
        binding.slide.setPageTransformer(transformer)
        binding.slide.offscreenPageLimit = 3
        binding.slide.clipToPadding = false
        binding.slide.clipChildren = false
        binding.slide.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun function() {
        val functions = arrayListOf<FunctionMain>()
        functions.add(FunctionMain(R.drawable.icon_doctor, "Đặt hẹn bác sĩ"))
        functions.add(FunctionMain(R.drawable.ic_action_schedule, "Lịch khám"))
        functions.add(FunctionMain(R.drawable.ic_action_department, "Khám theo bệnh viện"))
        functions.add(FunctionMain(R.drawable.ic_action_results, "Kết quả khám bệnh"))
        functions.add(FunctionMain(R.drawable.ic_action_service, "Khám dịch vụ"))
        functions.add(FunctionMain(R.drawable.ic_action_history, "Lịch sử khám bệnh"))
        functions.add(FunctionMain(R.drawable.ic_action_contact, "Liên hệ"))
        functions.add(FunctionMain(R.drawable.ic_action_medicine, "Thông tin thuốc"))

        val adapter = FunctionHomeAdapter(functions)
        adapter.onClickItem = {
            onClickFunction(it, requireActivity())
        }
        binding.rcvFunctionMain.adapter = adapter
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
    )= FragmentHomeBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }

}