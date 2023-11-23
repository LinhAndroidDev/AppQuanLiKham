package com.example.appkhambenh.ui.ui.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityHomeBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.csyt.ConnectCsytActivity
import com.example.appkhambenh.ui.ui.user.doctor.SearchDoctorActivity
import com.example.appkhambenh.ui.ui.user.home.FragmentHome
import com.example.appkhambenh.ui.ui.user.home.HomeViewModel
import com.example.appkhambenh.ui.ui.user.navigation.information.FragmentInformation
import com.example.appkhambenh.ui.ui.user.navigation.notification.FragmentNotification
import com.example.appkhambenh.ui.ui.user.navigation.setting.FragmentSetting
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.animRotation45
import com.example.appkhambenh.ui.utils.animRotationBack0
import com.google.android.material.bottomsheet.BottomSheetBehavior

@Suppress("DEPRECATION")
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    var isRotateAdd = false
    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.bottomSheet.layoutBook) }
    private val bottomSelectLanguage by lazy { BottomSheetBehavior.from(binding.bottomSelectLanguage.layoutSelectLanguage) }

    companion object {
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

        binding.changeIdHome.setOnTouchListener { _, _ -> true }

        replaceFragment(FragmentHome())

        initBottomBook()

        initBottomSelectLanguage()

        binding.bottomNvg.setOnItemSelectedListener {
            if (isRotateAdd) {
                isRotateAdd = false
                animRotationBack0(binding.imgAdd)
                collapsedBottomBook()
            }
            val fmCurrent = supportFragmentManager.findFragmentById(R.id.changeIdHome)
            when (it.itemId) {
                R.id.home -> if (fmCurrent !is FragmentHome) replaceFragment(FragmentHome())
                R.id.profile -> if (fmCurrent !is FragmentInformation) replaceFragment(
                    FragmentInformation()
                )
                R.id.register -> rotateIconRegister()
                R.id.notification -> if (fmCurrent !is FragmentNotification) replaceFragment(
                    FragmentNotification()
                )
                R.id.setting -> if (fmCurrent !is FragmentSetting) replaceFragment(FragmentSetting())
            }
            true
        }

        binding.registerAppoint.setOnClickListener {
            rotateIconRegister()
        }

        binding.layoutCoverBottom.setOnTouchListener { _, _ -> true }
    }

    private fun rotateIconRegister() {
        if (isRotateAdd) {
            isRotateAdd = false
            animRotationBack0(binding.imgAdd)
            collapsedBottomBook()
        } else {
            isRotateAdd = true
            animRotation45(binding.imgAdd)
            expandBottomBook()
        }
        binding.bottomNvg.menu.findItem(R.id.register).isChecked = true
    }

    private fun replaceFragment(fm: Fragment) {
        val fg = supportFragmentManager.beginTransaction()
        fg.replace(R.id.changeIdHome, fm).commit()
    }

    internal fun goToEditProfile() {
        replaceFragment(FragmentInformation())
        binding.bottomNvg.menu.findItem(R.id.profile).isChecked = true
    }

    internal fun hideIconBook() {
        binding.registerAppoint.visibility = View.GONE
        binding.layoutCoverBottom.visibility = View.VISIBLE
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
            isRotateAdd = false
            animRotationBack0(binding.imgAdd)
            collapsedBottomBook()
            true
        }

        binding.bottomSheet.bottomGoToDoctor.setOnClickListener {
            val intent = Intent(this@HomeActivity, SearchDoctorActivity::class.java)
            startActivity(intent)
        }

        binding.bottomSheet.bottomCsyt.setOnClickListener {
            val intent = Intent(this@HomeActivity, ConnectCsytActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("ClickableViewAccessibility", "CommitPrefEdits")
    private fun initBottomSelectLanguage() {

        val language = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.LANGUAGE, "vi").toString()
        if(language == "vi"){
            binding.bottomSelectLanguage.vietnamese.selectLanguage()
            binding.bottomSelectLanguage.english.unSelectLanguage()
        }else{
            binding.bottomSelectLanguage.vietnamese.unSelectLanguage()
            binding.bottomSelectLanguage.english.selectLanguage()
        }

        binding.bottomSelectLanguage.vietnamese.setText("Tiếng Việt")
        binding.bottomSelectLanguage.english.setText("English")

        binding.bottomSelectLanguage.vietnamese.setOnClickListener {
            viewModel.mPreferenceUtil.defaultPref()
                .edit().putString(PreferenceKey.LANGUAGE, "vi").apply()
            setLanguage(this, "vi")
            binding.bottomSelectLanguage.vietnamese.selectLanguage()
            binding.bottomSelectLanguage.english.unSelectLanguage()
            changeLanguageText()
        }

        binding.bottomSelectLanguage.english.setOnClickListener {
            viewModel.mPreferenceUtil.defaultPref()
                .edit().putString(PreferenceKey.LANGUAGE, "en").apply()
            setLanguage(this, "en")
            binding.bottomSelectLanguage.english.selectLanguage()
            binding.bottomSelectLanguage.vietnamese.unSelectLanguage()
            changeLanguageText()
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
    private fun changeLanguageText(){
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdHome)
        if(fm is FragmentSetting){
            fm.changeLanguageTextSetting()
        }

        binding.bottomNvg.menu.findItem(R.id.home).title = getString(R.string.home)
        binding.bottomNvg.menu.findItem(R.id.profile).title = getString(R.string.profile)
        binding.bottomNvg.menu.findItem(R.id.register).title = getString(R.string.book_examination)
        binding.bottomNvg.menu.findItem(R.id.notification).title = getString(R.string.notification)
        binding.bottomNvg.menu.findItem(R.id.setting).title = getString(R.string.setting)

        binding.bottomSheet.titleBottomBook.text = getString(R.string.book_now)
        binding.bottomSheet.titleDoctorBook.text = getString(R.string.go_to_doctor)
        binding.bottomSheet.noteDoctorBook.text = getString(R.string.note_doctor)
        binding.bottomSheet.titleServiceBook.text = getString(R.string.health_services)
        binding.bottomSheet.noteServiceBook.text = getString(R.string.note_health_services)
        binding.bottomSheet.titleCsytBook.text = getString(R.string.csyt)
        binding.bottomSheet.noteCsytBook.text = getString(R.string.note_csyt)
    }

    override fun onBackPressed() {
        val fmCurrent = supportFragmentManager.findFragmentById(R.id.changeIdHome)
        if (fmCurrent is FragmentHome) {
            super.onBackPressed()
        } else {
            replaceFragment(FragmentHome())
            binding.bottomNvg.menu.findItem(R.id.home).isChecked = true
            if (isRotateAdd) {
                isRotateAdd = false
                animRotationBack0(binding.imgAdd)
                collapsedBottomBook()
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityHomeBinding.inflate(inflater)
}