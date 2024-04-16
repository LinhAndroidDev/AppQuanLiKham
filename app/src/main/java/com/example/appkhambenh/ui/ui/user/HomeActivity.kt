package com.example.appkhambenh.ui.ui.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityHomeBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.hospital.HospitalActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.FragmentHome
import com.example.appkhambenh.ui.ui.user.home.HomeViewModel
import com.example.appkhambenh.ui.ui.user.navigation.communication.FragmentCommunity
import com.example.appkhambenh.ui.ui.user.navigation.notification.FragmentNotification
import com.example.appkhambenh.ui.ui.user.navigation.setting.FragmentSetting
import com.example.appkhambenh.ui.utils.animRotation45
import com.example.appkhambenh.ui.utils.animRotationBack0
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    private var isExpandBook = false
    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.bottomSheet.layoutBook) }
    private val bottomSelectLanguage by lazy { BottomSheetBehavior.from(binding.bottomSelectLanguage.layoutSelectLanguage) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

        binding.changeIdHome.setOnTouchListener { _, _ -> true }
        replaceFragment(FragmentHome(), R.id.changeIdHome)
        initBottomBook()
        initBottomSelectLanguage()

        binding.bottomNvg.apply {
            isEnabled = false
            isClickable = false
            isFocusable = false
            isFocusableInTouchMode = false
            setOnClickListener(null)
        }

        binding.bottomNvg.setOnItemSelectedListener {
            val fmCurrent = supportFragmentManager.findFragmentById(R.id.changeIdHome)
            when (it.itemId) {
                R.id.home -> {
                    checkExpandBook()
                    if (fmCurrent !is FragmentHome) {
                        replaceFragment(FragmentHome(), R.id.changeIdHome)
                    }
                }

                R.id.profile -> {
                    checkExpandBook()
                    if (fmCurrent !is FragmentCommunity) {
                        replaceFragment(FragmentCommunity(), R.id.changeIdHome)
                    }
                }

                R.id.register -> rotateIconRegister()

                R.id.notification -> {
                    checkExpandBook()
                    if (fmCurrent !is FragmentNotification) {
                        replaceFragment(FragmentNotification(), R.id.changeIdHome)
                    }
                }

                R.id.setting -> {
                    checkExpandBook()
                    if (fmCurrent !is FragmentSetting) {
                        replaceFragment(FragmentSetting(), R.id.changeIdHome)
                    }
                }
            }
            true
        }

        binding.registerAppoint.setOnClickListener {
            rotateIconRegister()
        }

        binding.layoutCoverBottom.setOnTouchListener { _, _ -> true }
    }

    private fun checkExpandBook() {
        if (isExpandBook) {
            isExpandBook = false
            animRotationBack0(binding.imgAdd)
            collapsedBottomBook()
        }
    }

    private fun rotateIconRegister() {
        if (isExpandBook) {
            isExpandBook = false
            animRotationBack0(binding.imgAdd)
            collapsedBottomBook()
        } else {
            isExpandBook = true
            animRotation45(binding.imgAdd)
            expandBottomBook()
        }
        binding.bottomNvg.menu.findItem(R.id.register).isChecked = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBottomBook() {
        bottomSheetBehavior.isGestureInsetBottomIgnored = false
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.bottomSheet.layoutCoverSheet.isEnabled = true
                    binding.bottomSheet.layoutCoverSheet.visibility = View.VISIBLE
                } else {
                    binding.bottomSheet.layoutCoverSheet.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.bottomSheet.layoutMainSheet.setOnTouchListener { _, _ -> true }

        binding.bottomSheet.layoutCoverSheet.setOnTouchListener { _, _ ->
            binding.bottomSheet.layoutCoverSheet.isEnabled = false
            isExpandBook = false
            animRotationBack0(binding.imgAdd)
            collapsedBottomBook()
            true
        }

        binding.bottomSheet.bottomGoToDoctor.setOnClickListener {
            val intent = Intent(this@HomeActivity, SearchDoctorActivity::class.java)
            startActivity(intent)
        }

        binding.bottomSheet.bottomCsyt.setOnClickListener {
            val intent = Intent(this@HomeActivity, HospitalActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("ClickableViewAccessibility", "CommitPrefEdits")
    private fun initBottomSelectLanguage() {

        when (sharePrefer.getLanguage()) {
            "vi" -> {
                unSelectLanguage()
                binding.bottomSelectLanguage.vietnamese.selectLanguage()
            }

            "en" -> {
                unSelectLanguage()
                binding.bottomSelectLanguage.english.selectLanguage()
            }

            "zh" -> {
                unSelectLanguage()
                binding.bottomSelectLanguage.chinese.selectLanguage()
            }

            "ja" -> {
                unSelectLanguage()
                binding.bottomSelectLanguage.japanese.selectLanguage()
            }

            "th" -> {
                unSelectLanguage()
                binding.bottomSelectLanguage.thailand.selectLanguage()
            }
        }

        binding.bottomSelectLanguage.vietnamese.setText("Tiếng Việt")
        binding.bottomSelectLanguage.english.setText("English")
        binding.bottomSelectLanguage.chinese.setText("中国人")
        binding.bottomSelectLanguage.japanese.setText("日本語")
        binding.bottomSelectLanguage.thailand.setText("แบบไทย")

        binding.bottomSelectLanguage.vietnamese.setOnClickListener {
            resetLanguage("vi")
            binding.bottomSelectLanguage.vietnamese.selectLanguage()
        }

        binding.bottomSelectLanguage.english.setOnClickListener {
            resetLanguage("en")
            binding.bottomSelectLanguage.english.selectLanguage()
        }

        binding.bottomSelectLanguage.chinese.setOnClickListener {
            resetLanguage("zh")
            binding.bottomSelectLanguage.chinese.selectLanguage()
        }

        binding.bottomSelectLanguage.japanese.setOnClickListener {
            resetLanguage("ja")
            binding.bottomSelectLanguage.japanese.selectLanguage()
        }

        binding.bottomSelectLanguage.thailand.setOnClickListener {
            resetLanguage("th")
            binding.bottomSelectLanguage.thailand.selectLanguage()
        }

        bottomSelectLanguage.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.bottomSelectLanguage.layoutCoverSheet.isEnabled = true
                    binding.bottomSelectLanguage.layoutCoverSheet.visibility = View.VISIBLE
                } else {
                    binding.bottomSelectLanguage.layoutCoverSheet.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.bottomSelectLanguage.imgClose.setOnClickListener {
            collapseBottomSelectLanguage()
        }

        binding.bottomSelectLanguage.btnClose.setOnClickListener {
            collapseBottomSelectLanguage()
        }

        binding.bottomSelectLanguage.layoutCoverSheet.setOnTouchListener { _, _ ->
            binding.bottomSelectLanguage.layoutCoverSheet.isEnabled = false
            collapseBottomSelectLanguage()
            true
        }

        binding.bottomSelectLanguage.layoutMainLanguage.setOnTouchListener { _, _ -> true }
    }

    private fun resetLanguage(language: String) {
        sharePrefer.saveLanguage(language)
        setLanguage(this, language)
        unSelectLanguage()
        changeLanguageText()
    }

    private fun unSelectLanguage() {
        binding.bottomSelectLanguage.apply {
            vietnamese.unSelectLanguage()
            english.unSelectLanguage()
            chinese.unSelectLanguage()
            japanese.unSelectLanguage()
            thailand.unSelectLanguage()
        }
    }

    internal fun expandBottomSelectLanguage() {
        bottomSelectLanguage.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun collapseBottomSelectLanguage() {
        bottomSelectLanguage.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun expandBottomBook() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun collapsedBottomBook() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @SuppressLint("SetTextI18n")
    private fun changeLanguageText() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdHome)
        if (fm is FragmentSetting) {
            fm.changeLanguageTextSetting()
        }
        binding.bottomNvg.menu.apply {
            findItem(R.id.home).title = getString(R.string.home)
            findItem(R.id.profile).title = getString(R.string.community)
            findItem(R.id.register).title = getString(R.string.book_examination)
            findItem(R.id.notification).title = getString(R.string.notification)
            findItem(R.id.setting).title = getString(R.string.setting)
        }
        binding.bottomSheet.apply {
            titleBottomBook.text = getString(R.string.book_now)
            titleDoctorBook.text = getString(R.string.go_to_doctor)
            noteDoctorBook.text = getString(R.string.note_doctor)
            titleServiceBook.text = getString(R.string.health_services)
            noteServiceBook.text = getString(R.string.note_health_services)
            titleCsytBook.text = getString(R.string.csyt)
            noteCsytBook.text = getString(R.string.note_csyt)
        }
        binding.bottomSelectLanguage.titleChangeLanguage.text = getString(R.string.select_language)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fmCurrent = supportFragmentManager.findFragmentById(R.id.changeIdHome)
        if (fmCurrent is FragmentHome) {
            super.onBackPressed()
        } else {
            replaceFragment(FragmentHome(), R.id.changeIdHome)
            binding.bottomNvg.menu.findItem(R.id.home).isChecked = true
            if (isExpandBook) {
                isExpandBook = false
                animRotationBack0(binding.imgAdd)
                collapsedBottomBook()
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityHomeBinding.inflate(inflater)
}