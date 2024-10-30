package com.example.appkhambenh.ui.ui.user.navigation.communication

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityQuestionBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.data.remote.model.CommentModel
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.navigation.communication.adapter.CommentQuestionAdapter

class QuestionActivity : BaseActivity<EmptyViewModel, ActivityQuestionBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.header.setTitle("Câu hỏi")
        initComment()
    }

    private fun initComment() {
        val list = arrayListOf<CommentModel>(
            CommentModel("Chào em", 1),
            CommentModel("Em nên dùng nước muối để rửa sạch. Kết hợp bôi kem kháng sinh, thuốc chống ngứa", 1),
            CommentModel("Bác sĩ cho em hỏi kem kháng sinh nên dùng loại nào ạ?", 0),
            CommentModel("Em dùng fucidin.\nChống ngứa dùng fexxofenadin", 1)
        )
        val adapter = CommentQuestionAdapter()
        adapter.items = list
        binding.rcvComment.adapter = adapter
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityQuestionBinding.inflate(inflater)
}