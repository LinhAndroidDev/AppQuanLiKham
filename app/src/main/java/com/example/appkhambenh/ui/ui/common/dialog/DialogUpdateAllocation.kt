package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogUpdateAllocationBinding
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest

class DialogUpdateAllocation : DialogFragment() {
    private var binding: DialogUpdateAllocationBinding? = null
    var allocationRequest: UpdateAllocationRequest? = null
    var errorMessage: ((String) -> Unit)? = null
    var update: (() -> Unit)? = null
    private val departments by lazy {
        arrayListOf(
            "Khoa Nội Tiết",
            "Khoa Nhi",
            "Khoa Ngoại",
            "Khoa Tai Mũi Họng",
            "Khoa Răng Hàm Mặt",
            "Khoa Mắt",
            "Khoa Da Liễu",
            "Khoa Tiêu Hoá",
            "Khoa Tim Mạch",
            "Khoa Thần Kinh"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogUpdateAllocationBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)

        binding?.facultyTreatment?.setUpListSpinner(departments)

        binding?.update?.setOnClickListener {
            if (textNotEmpty()) {
                allocationRequest = UpdateAllocationRequest(
                    bed = binding?.bed?.getText().toString(),
                    facultyTreatment = departments[binding?.facultyTreatment?.indexSelected ?: 0],
                    room = binding?.room?.getText().toString()
                )
                update?.invoke()
            } else {
                errorMessage?.invoke("Bạn chưa nhập đủ thông tin")
            }
        }
    }

    private fun textNotEmpty(): Boolean {
        return binding?.bed?.getText()?.isNotEmpty() == true &&
                binding?.room?.getText()?.isNotEmpty() == true
    }
}