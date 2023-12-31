package com.example.appkhambenh.ui.ui.user.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentHomeBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.Doctor
import com.example.appkhambenh.ui.model.FunctionMain
import com.example.appkhambenh.ui.model.MedicalHandbook
import com.example.appkhambenh.ui.ui.doctor.statistical.StatisticalActivity
import com.example.appkhambenh.ui.ui.doctor.time_working.EditTimeWorkActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.appointment.AppointmentActivity
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.csyt.CsytActivity
import com.example.appkhambenh.ui.ui.user.csyt.InfoCsytActivity
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.adapter.DoctorHighlightAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.FunctionHomeAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.ImageAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.MedicalHandBookAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.TopCsytAdapter
import com.example.appkhambenh.ui.ui.user.qr.QrActivity
import com.example.appkhambenh.ui.utils.onClickFunction
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.abs

@Suppress("DEPRECATION")
class FragmentHome : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val timer by lazy { Timer() }

    companion object {
        const val URI_AVATAR = "URI_AVATAR"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("SetTextI18n")
    override fun bindData() {
        super.bindData()

        viewModel.getUserInfo(
            convertToRequestBody(sharePrefer.getUserId().toString()),
            requireActivity()
        )

        viewModel.loadingLiveData.observe(this) {
            if (it) {
                loading.show()
            } else {
                lifecycleScope.launch {
                    delay(500L)
                    withContext(Dispatchers.Main) {
                        loading.dismiss()
                    }
                    if (!isOnline(requireActivity())) {
                        show(getString(R.string.check_internet))
                    } else {
                        lifecycleScope.launch {
                            binding.txtUserBirth.text =
                                if (sharePrefer.getUserType() != 2) sharePrefer.getUserBirth() else sharePrefer.getUserAddress()
                            binding.txtUserName.text = sharePrefer.getUserName()

                            sharePrefer.getUserAvatar().let {  avt->
                                if (avt.isNotEmpty()) {
                                    Picasso.get().load(avt)
                                        .error(R.drawable.user_ad)
                                        .placeholder(R.drawable.user_ad)
                                        .into(binding.avatarUser)
                                }
                            }

                            delay(1000L)
                            withContext(Dispatchers.Main) {
                                scrollInfinity()
                                binding.slide.visibility = View.VISIBLE
                                val animEnter = AnimationUtils.loadAnimation(
                                    requireActivity(),
                                    R.anim.enter_view
                                )
                                binding.slide.startAnimation(animEnter)
                            }

                            delay(500L)
                            withContext(Dispatchers.Main) {
                                binding.titleDoctorHighlight.visibility = View.VISIBLE
                                binding.titleHandbook.visibility = View.VISIBLE
                                binding.titleCsyt.visibility = View.VISIBLE

                                doctorHighlight()

                                medicalHandbook()

                                topCsyt()
                            }
                        }
                    }
                }
            }
        }

        viewModel.userLiveData.observe(this) {
            if (it.result?.type != 0) {
                if (it.result?.type == 2) {
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
        }
    }

    @SuppressLint("IntentReset")
    private fun initUi() {

        function()

        binding.avatarUser.setOnClickListener {
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

    private fun medicalHandbook() {
        val handbooks = arrayListOf<MedicalHandbook>()
        handbooks.add(
            MedicalHandbook(
                "5 Nguyên nhân bướu cổ ở nam giới và 3 cách điều trị",
                "https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/5-nguyen-nhan-buou-co-o-nam-gioi-va-3-cach-dieu-tri-ivie-1_57509f24_c8ec_4abb_ad49_f9eb77d95503.jpg",
                "23/08/2023",
                "8 phút đọc"
            )
        )
        handbooks.add(
            MedicalHandbook(
                "5 Địa chỉ xét nghiệm dị nguyên tại Hà Nội",
                "https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/5-dia-chi-xet-nghiem-di-nguyen-tai-ha-noi-ivie-1_ae10d1d9_0f82_4974_8f67_ff82902f486e.jpg",
                "14/10/2023",
                "15 phút đọc"
            )
        )
        handbooks.add(
            MedicalHandbook(
                "Xét nghiệm lao phổi giá bao nhiêu?",
                "https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/chi-phi-xet-nghiem-lao-phoi-tu-80-000-400-000-dong_e0249544_ce24_4854_bc4c_7620dcf17cef.jpg",
                "05/11/2023",
                "9 phút đọc"
            )
        )
        val adapter = MedicalHandBookAdapter(requireActivity(), handbooks)
        adapter.url = {
            if (it.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(it)
                startActivity(intent)
            }
        }
        binding.rcvMedicalHandbook.adapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun doctorHighlight() {
        binding.seeMoreDoctor.layout.setOnClickListener {
            val intent = Intent(requireActivity(), SearchDoctorActivity::class.java)
            startActivity(intent)
        }
        val doctors = arrayListOf<Doctor>()
        for (i in 0 until 4) {
            doctors.add(
                Doctor(
                    "Hoàng Quang Huy",
                    "PSG.TS",
                    R.drawable.img_doctor,
                    "Chuyên khoa Tim mạch",
                    "Bệnh Viện Đa Khoa Tp.Hà Nội"
                )
            )
        }
        val adapter = DoctorHighlightAdapter(doctors)
        adapter.onClickItem = {
            val intent = Intent(requireActivity(), InfoDoctorActivity::class.java)
            startActivity(intent)
        }
        binding.slideHighlightDoctor.adapter = adapter
        setUpTransformer(binding.slideHighlightDoctor, 5, 1f, 0f)
    }

    private fun topCsyt() {
        binding.seeMoreCsyt.layout.setOnClickListener {
            val intent = Intent(requireActivity(), CsytActivity::class.java)
            startActivity(intent)
        }

        val csyts = arrayListOf<Int>()
        val adapterTopCsyt = TopCsytAdapter(requireActivity(), csyts)
        adapterTopCsyt.onCLickItem = {
            if (it) {
                val intent = Intent(requireActivity(), InfoCsytActivity::class.java)
                startActivity(intent)
            }
        }
        for (i in 0..10) {
            csyts.add(1)
        }
        binding.rcvTopCsyt.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = adapterTopCsyt
        }
    }

    private fun scrollInfinity() {

        setUpTransformer(binding.slide, 0, 0.85f, 0.18f)

        val images = arrayListOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3)
        images.add(R.drawable.slide1)
        images.add(0, R.drawable.slide3)
        val slideAdapter = ImageAdapter(requireActivity(), images)
        binding.slide.adapter = slideAdapter

        val recyclerView = binding.slide.getChildAt(0) as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val itemCount = binding.slide.adapter?.itemCount ?: 0

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun setUpTransformer(vpg2: ViewPager2, margin: Int, a: Float, b: Float) {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(margin))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = a + r * b
        }
        vpg2.setPageTransformer(transformer)
        vpg2.offscreenPageLimit = 3
        vpg2.clipToPadding = false
        vpg2.clipChildren = false
        vpg2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun function() {
        val functions = arrayListOf<FunctionMain>()
        functions.add(FunctionMain(R.drawable.icon_doctor, getString(R.string.appoint_doctor)))
        functions.add(
            FunctionMain(
                R.drawable.ic_action_schedule,
                getString(R.string.examination_schedule)
            )
        )
        functions.add(
            FunctionMain(
                R.drawable.ic_action_department,
                getString(R.string.hospital_examination)
            )
        )
        functions.add(
            FunctionMain(
                R.drawable.ic_action_results,
                getString(R.string.examination_results)
            )
        )
        functions.add(FunctionMain(R.drawable.ic_action_service, getString(R.string.check_service)))
        functions.add(
            FunctionMain(
                R.drawable.ic_action_history,
                getString(R.string.medical_examination_history)
            )
        )
        functions.add(FunctionMain(R.drawable.ic_action_contact, getString(R.string.contact)))
        functions.add(
            FunctionMain(
                R.drawable.ic_action_medicine,
                getString(R.string.drug_information)
            )
        )

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
            intent.putExtra(URI_AVATAR, data?.data.toString())
            startActivity(intent)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentHomeBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        loading.dismiss()
    }

}