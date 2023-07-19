package com.example.appkhambenh.ui.ui.user.appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class FragmentAppointment : BaseFragment<EmptyViewModel, FragmentAppointmentBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        val avatar = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "").toString()
        Picasso.get().load(avatar)
            .placeholder(R.drawable.ad)
            .error(R.drawable.ad)
            .into(binding.avatarAppointment)

        val date = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.DATE_APPOINTMENT, "").toString()
        binding.date.text = date

        val hour = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.HOUR_APPOINTMENT, "").toString()
        binding.hour.text = hour

        binding.txtUseNameAppointment.text =
            viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_NAME,"")

        binding.backAppointment.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAppointmentBinding.inflate(inflater)
}