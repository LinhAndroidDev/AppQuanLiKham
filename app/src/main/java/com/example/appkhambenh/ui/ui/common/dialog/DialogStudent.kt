package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogStudentBinding
import com.example.appkhambenh.ui.ui.login.FragmentLogin
import com.example.appkhambenh.ui.ui.login.Student
import com.example.appkhambenh.ui.ui.login.StudentAdapter

class DialogStudent : DialogFragment() {
    private lateinit var binding: DialogStudentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogStudentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(resources.getDimensionPixelSize(R.dimen.dimen_330), resources.getDimensionPixelSize(R.dimen.dimen_400))
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)

        val students = arguments?.getParcelableArrayList<Student>(FragmentLogin.LIST_STUDENT)
        students?.let {
            val studentAdapter = StudentAdapter()
            studentAdapter.items = it
            binding.rcvStudent.adapter = studentAdapter
        }
    }
}