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
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.ui.doctor.FragmentMedicalExaminationHistory

class DialogUpdateAllocation : DialogFragment() {
    private var binding: DialogUpdateAllocationBinding? = null
    var allocationRequest: UpdateAllocationRequest? = null
    var errorMessage: ((String) -> Unit)? = null
    var update: (() -> Unit)? = null
    private val departments by lazy {
        arrayListOf(
            "Khoa Nội tiết",
            "Khoa Nhi",
            "Khoa Ngoại",
            "Khoa Tai mũi họng",
            "Khoa Răng hàm mặt",
            "Khoa Mắt",
            "Khoa Da liễu",
            "Khoa Tiêu hoá",
            "Khoa Tim Mạch",
            "Khoa Thần kinh"
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

        val medicalHistory = arguments?.getParcelable<MedicalHistoryResponse.Data>(
            FragmentMedicalExaminationHistory.MEDICAL_HISTORY)
        medicalHistory?.let {
            medicalHistory.bed?.let { binding?.bed?.setText(it) }
            medicalHistory.room?.let { binding?.room?.setText(it) }
            medicalHistory.facultyTreatment?.let {
                binding?.facultyTreatment?.setUpIndexSpinner(departments.indexOf(it))
            }
        }

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