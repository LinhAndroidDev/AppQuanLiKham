package com.example.appkhambenh.ui.ui.user.navigation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentNotificationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.ui.user.navigation.notification.adapter.NotificationAdapter

class FragmentNotification : BaseFragment<NotificationViewModel, FragmentNotificationBinding>(){
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = arrayListOf<RegisterChecking>()
        for (i in 0 until 10) {
            list.add(
                RegisterChecking(
                    "Khám tự nguyện",
                    "Khoa điều trị tạm thời",
                    "Nguyễn Hữu Linh",
                    "Thứ tư, 18 tháng 3",
                    "10:00",
                    "Tái khám sau phẫu thuật"
                )
            )
        }
        notificationAdapter = NotificationAdapter(list)
        notificationAdapter.reverseList()
        binding.rcvNotification.adapter = notificationAdapter

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentNotificationBinding.inflate(inflater)

}
