package com.example.appkhambenh.ui.ui.user.navigation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentNotificationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.navigation.notification.adapter.NotificationAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey

class FragmentNotification : BaseFragment<NotificationViewModel, FragmentNotificationBinding>(){
    lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            binding.rcvNotification.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
            notificationAdapter = NotificationAdapter(it)
            notificationAdapter.reverseList()
            binding.rcvNotification.adapter = notificationAdapter
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentNotificationBinding.inflate(inflater)

}
