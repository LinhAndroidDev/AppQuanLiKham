package com.example.appkhambenh.ui.ui.user.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.example.appkhambenh.ui.model.MedicalHandbook
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.hospital.HospitalActivity
import com.example.appkhambenh.ui.ui.user.hospital.InfoHospitalActivity
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.adapter.DoctorHighlightAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.FunctionHomeAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.ImageAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.MedicalHandBookAdapter
import com.example.appkhambenh.ui.ui.user.home.adapter.TopCsytAdapter
import com.example.appkhambenh.ui.utils.ConvertUtils
import com.example.appkhambenh.ui.utils.functionHome
import com.example.appkhambenh.ui.utils.isOnline
import com.example.appkhambenh.ui.utils.onClickFunction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.abs

@AndroidEntryPoint
class FragmentHome : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val timer by lazy { Timer() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("SetTextI18n")
    override fun bindData() {
        super.bindData()

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getUserInfo(sharePrefer.getUserId())
            viewModel.isSuccessful.collect { isSuccessful ->
                if(isSuccessful) {
                    viewModel.avatar.collect {
                        it?.let {
                            val bitmap = BitmapFactory.decodeStream(it)
                            binding.avatarUser.setImageBitmap(bitmap)
                            sharePrefer.saveUserAvatar(ConvertUtils.bitmapToBase64(bitmap))
                        }
                    }
                }

            }
        }

        viewModel.loading.observe(this) {
            if (it) {
                showLoading()
            } else {
                lifecycleScope.launch {
                    delay(500L)
                    withContext(Dispatchers.Main) {
                        dismissLoading()
                    }
                    if (activity?.isOnline() == false) {
                        show(getString(R.string.check_internet))
                    } else {
                        lifecycleScope.launch {
                            binding.txtUserBirth.text = sharePrefer.getUserBirth()
                            binding.txtUserName.text = sharePrefer.getUserName()
                            delay(1000L)
                            withContext(Dispatchers.Main) {
                                showSlideAutoRun()
                            }
                            delay(500L)
                            withContext(Dispatchers.Main) {
                                initDoctorHighlight()
                                initMedicalHandbook()
                                initTopCsyt()
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("IntentReset")
    private fun initUi() {
        function()
        onClickView()
    }

    private fun onClickView() {
        binding.avatarUser.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.seeMoreDoctor.layout.setOnClickListener {
            val intent = Intent(requireActivity(), SearchDoctorActivity::class.java)
            startActivity(intent)
        }

        binding.seeMoreCsyt.layout.setOnClickListener {
            val intent = Intent(requireActivity(), HospitalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initMedicalHandbook() {
        binding.titleHandbook.visibility = View.VISIBLE
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
        val adapter = MedicalHandBookAdapter(requireActivity())
        adapter.items = handbooks
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
    private fun initDoctorHighlight() {
        binding.titleDoctorHighlight.visibility = View.VISIBLE
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
        val adapter = DoctorHighlightAdapter()
        adapter.items = doctors
        adapter.onClickItem = {
            val intent = Intent(requireActivity(), InfoDoctorActivity::class.java)
            startActivity(intent)
        }
        binding.slideHighlightDoctor.adapter = adapter
        setUpTransformer(binding.slideHighlightDoctor, 5, 1f, 0f)
    }

    private fun initTopCsyt() {
        binding.titleCsyt.visibility = View.VISIBLE
        val adapterTopCsyt = TopCsytAdapter()
        adapterTopCsyt.onCLickItem = {
            val intent = Intent(requireActivity(), InfoHospitalActivity::class.java)
            startActivity(intent)
        }
        binding.rcvTopCsyt.apply {
            adapter = adapterTopCsyt
        }
    }

    private fun initScrollInfinity() {
        setUpTransformer(binding.slide, 0, 0.85f, 0.18f)

        val images = arrayListOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3)
        images.add(R.drawable.slide1)
        images.add(0, R.drawable.slide3)
        val slideAdapter = ImageAdapter(requireActivity())
        slideAdapter.items = images
        binding.slide.adapter = slideAdapter

        //create indicator
        val paramsIndicator = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        ).apply {
            setMargins(10, 0, 10, 0)
            width = 13
            height = 13
        }
        val indicators =
            Array(images.size) { ImageView(requireActivity()) }
        if (images.size > 1) {
            indicators.forEach {
                it.setImageResource(R.drawable.circle_grey)
                binding.indicator.addView(it, paramsIndicator)
            }
            indicators[0].visibility = View.INVISIBLE
            indicators[images.size - 1].visibility = View.INVISIBLE

            binding.slide.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    indicators.mapIndexed { index, imageView ->
                        lifecycleScope.launch {
                            delay(200L)
                            withContext(Dispatchers.Main) {
                                imageView.setImageResource(
                                    if (position == index) R.drawable.circle_bg else R.drawable.circle_grey
                                )
                            }
                        }
                    }
                }
            })
        }

        //set scroll infinity
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
    }

    private fun showSlideAutoRun() {
        initScrollInfinity()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                binding.slide.currentItem = binding.slide.currentItem + 1
            }
        }, 0, 3000)

        binding.slide.visibility = View.VISIBLE
        val animEnter = AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.enter_view
        )
        binding.slide.startAnimation(animEnter)
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
        val adapter = FunctionHomeAdapter()
        adapter.items = functionHome()
        adapter.onClickItem = {
            onClickFunction(it, requireActivity())
        }
        binding.rcvFunctionMain.adapter = adapter
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
        dismissLoading()
    }

}