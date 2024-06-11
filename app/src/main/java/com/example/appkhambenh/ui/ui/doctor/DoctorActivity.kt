package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityDoctorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class DoctorActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDoctorBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(FragmentHomeDoctor(), "FragmentHomeDoctor")
        onCLickView()
    }

    private fun onCLickView() {

        binding.close.setOnClickListener {
            binding.drawerView.close()
        }

        binding.navHome.setOnClickListener {
            val fragmentCurrent = supportFragmentManager.findFragmentById(R.id.changeIdDoctorVn)
            if (fragmentCurrent !is FragmentHomeDoctor) {
                replaceFragment(FragmentHomeDoctor(), "FragmentHomeDoctor")
            }
            checkItemNavigation(binding.navHome)
        }

        binding.navAdmin.setOnClickListener {
            val fragmentCurrent = supportFragmentManager.findFragmentById(R.id.changeIdDoctorVn)
            if (fragmentCurrent !is FragmentAdminDoctor) {
                replaceFragment(FragmentAdminDoctor(), "FragmentAdminDoctor")
            }
            checkItemNavigation(binding.navAdmin)
        }

        binding.navAppoint.setOnClickListener {
            val fragmentCurrent = supportFragmentManager.findFragmentById(R.id.changeIdDoctorVn)
            if (fragmentCurrent !is FragmentAppointDoctor) {
                replaceFragment(FragmentAppointDoctor(), "FragmentAppointDoctor")
            }
            checkItemNavigation(binding.navAppoint)
        }
    }

    private fun checkItemNavigation(v: CustomNavigationDoctor) {
        uncheckItemNavigation()
        v.checkItem()
        binding.root.postDelayed(400) {
            binding.drawerView.close()
        }
    }

    private fun uncheckItemNavigation() {
        binding.navHome.unCheckItem()
        binding.navAdmin.unCheckItem()
        binding.navAppoint.unCheckItem()
    }

    private fun replaceFragment(fm: Fragment, tag: String) {
        val fg = supportFragmentManager.beginTransaction()
        fg.addToBackStack(null)
        fg.replace(R.id.changeIdDoctorVn, fm, tag).addToBackStack(null).commit()
    }

    internal fun openNavigationDrawer() {
        binding.drawerView.open()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.changeIdDoctorVn)) {
            is FragmentHomeDoctor -> {
                finish()
            }
            is FragmentAdminDoctor, is FragmentAppointDoctor -> {
                replaceFragment(FragmentHomeDoctor(), "FragmentHomeDoctor")
                checkItemNavigation(binding.navHome)
            }

            is FragmentTreatmentManagement, is FragmentEditInfoPatient -> {
                super.onBackPressed()
            }
        }
    }
}