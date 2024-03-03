package com.example.appkhambenh.ui.ui.user.navigation.communication

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityCreateQuestionBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel

class CreateQuestionActivity : BaseActivity<EmptyViewModel, ActivityCreateQuestionBinding>() {
    private var isMale = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.header.setTitle("Đặt câu hỏi")

        binding.female.genderFemale()
        binding.male.stsSelected()
        binding.female.stsUnSelected()

        binding.male.setOnClickListener {
            if(!isMale) {
                isMale = true
                binding.male.stsSelected()
                binding.female.stsUnSelected()
            }
        }

        binding.female.setOnClickListener {
            if(isMale) {
                isMale = false
                binding.male.stsUnSelected()
                binding.female.stsSelected()
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityCreateQuestionBinding.inflate(inflater)
}