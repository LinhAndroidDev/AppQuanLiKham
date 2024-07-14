package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityDoctorBinding
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class DoctorActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDoctorBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(FragmentHomeDoctor(), "FragmentHomeDoctor")

        SharePreferenceRepositoryImpl(this).getRollUser().let { role ->
            when(role) {
                2 -> {
                    binding.navAccount.isVisible = false
                }

                3 -> {
                    binding.navAccount.isVisible = false
                }

                4 -> {
                    binding.navAdmin.isVisible = false
                    binding.navAccount.isVisible = false
                }

                5 -> {
                    binding.navAppoint.isVisible = false
                    binding.navAccount.isVisible = false
                }

                6 -> {
                    binding.navAppoint.isVisible = false
                    binding.navAccount.isVisible = false
                }

                7 -> {
                    binding.navAdmin.isVisible = false
                    binding.navAccount.isVisible = false
                }

                else -> {

                }
            }
        }
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

        binding.navAccount.setOnClickListener {
            val fragmentCurrent = supportFragmentManager.findFragmentById(R.id.changeIdDoctorVn)
            if (fragmentCurrent !is FragmentAccount) {
                replaceFragment(FragmentAccount(), "FragmentAccount")
            }
            checkItemNavigation(binding.navAccount)
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
        binding.navAccount.unCheckItem()
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
            is FragmentAdminDoctor, is FragmentAppointDoctor, is FragmentAccount -> {
                replaceFragment(FragmentHomeDoctor(), "FragmentHomeDoctor")
                checkItemNavigation(binding.navHome)
            }

            is FragmentTreatmentManagement,
            is FragmentMedicalExaminationHistory,
            is FragmentEditInfoPatient,
            is FragmentListMedicalRecord,
            is FragmentPrescription-> {
                super.onBackPressed()
            }
        }
    }
}