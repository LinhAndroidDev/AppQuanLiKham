package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogAddAccountBinding
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import com.example.appkhambenh.ui.utils.PersonalInformation
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl

class DialogAddAccount : DialogFragment() {
    private var binding: DialogAddAccountBinding? = null
    var addAccountRequest: AddAccountRequest? = null
    var errorMessage: ((String) -> Unit)? = null
    var addAccount: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogAddAccountBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        binding?.role?.setUpListSpinner(PersonalInformation.rolls())

        binding?.closeDialog?.setOnClickListener {
            dismiss()
        }

        binding?.cancel?.setOnClickListener {
            dismiss()
        }

        binding?.confirm?.setOnClickListener {
            if(binding?.email?.getText()?.isEmpty() == true) {
                errorMessage?.invoke("Bạn chưa nhập đầy đủ thông tin")
            } else {
                addAccountRequest = AddAccountRequest(
                    email = binding?.email?.getText().toString(),
                    roleId = (binding?.role?.indexSelected ?: 0) + 1
                )
                addAccount?.invoke()
            }
        }
    }
}