package com.example.appkhambenh.ui.ui.user.navigation.notification

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityNotificationBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.ui.user.navigation.notification.adapter.NotificationAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey

class NotificationActivity : BaseActivity<NotificationViewModel, ActivityNotificationBinding>() {
    lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindData() {
        super.bindData()

        viewModel.getListNotification(
            convertToRequestBody(
                viewModel.mPreferenceUtil.defaultPref()
                    .getInt(PreferenceKey.USER_ID, -1).toString()
            )
        )

        viewModel.listAppointmentLiveData.observe(this){
            notificationAdapter = NotificationAdapter(it)
            notificationAdapter.reverseList()
            binding.rcvNotification.adapter = notificationAdapter
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityNotificationBinding.inflate(inflater)
}