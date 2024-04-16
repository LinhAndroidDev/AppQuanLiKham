package com.example.appkhambenh.ui.ui.user.navigation.communication

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
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

        binding.progressAge.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                binding.tvAge.text = "$progress Tuổi"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityCreateQuestionBinding.inflate(inflater)
}