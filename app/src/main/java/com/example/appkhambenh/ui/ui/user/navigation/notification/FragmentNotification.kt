package com.example.appkhambenh.ui.ui.user.navigation.notification

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentNotificationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.navigation.notification.adapter.NotificationAdapter
import java.util.Locale

class FragmentNotification : BaseFragment<NotificationViewModel, FragmentNotificationBinding>(){
    private lateinit var notificationAdapter: NotificationAdapter

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.setTitle(getString(R.string.notification).uppercase(Locale.getDefault()))
        binding.header.hideBtnBack()
        binding.header.visibleDelete()
        binding.rcvNotification.adapter = NotificationAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentNotificationBinding.inflate(inflater)

}
