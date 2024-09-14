package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.databinding.FragmentAccountBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.model.AccountModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogAddAccount
import com.example.appkhambenh.ui.ui.doctor.adapter.AccountAdapter
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentAccountViewModel
import com.example.appkhambenh.ui.utils.PersonalInformation
import com.example.appkhambenh.ui.utils.initTextComplete
import com.example.appkhambenh.ui.utils.textNullOrEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentAccount : BaseFragment<FragmentAccountViewModel, FragmentAccountBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        onClickView()
    }

    private fun onClickView() {
        binding.reload.setOnClickListener {
            viewModel.getAccount()
        }

        binding.searchAccount.setOnClickListener {
            viewModel.getAccount(
                fullname = textNullOrEmpty(binding.nameAccount.getText()),
                email = textNullOrEmpty(binding.emailAccount.getText()),
                roleId = if (binding.role.text.toString()
                        .isNullOrEmpty()
                ) null else PersonalInformation.rolls()
                    .indexOf(binding.role.text.toString()) + 1
            )
        }

        binding.addAccount.setOnClickListener {
            val dialog = DialogAddAccount()
            dialog.show(parentFragmentManager, "DialogAddAccount")
            dialog.addAccount = {
                viewModel.addAccount(dialog.addAccountRequest!!)
                dialog.dismiss()
            }
            dialog.errorMessage = { show(it) }
        }
    }

    private fun initView() {
        binding.role.initTextComplete(requireActivity(), PersonalInformation.rolls())
        lifecycleScope.launch {
            delay(1000L)
            withContext(Dispatchers.Main) {
                viewModel.getAccount()
            }
        }
    }

    override fun bindData() {
        super.bindData()

        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                viewModel.accounts.collect {
                    initListAccount(it)
                }
            }
        }
    }

    private fun initListAccount(accountModels: ArrayList<AccountModel>?) {
        accountModels?.let {
            val accountAdapter = AccountAdapter()
            accountAdapter.items = accountModels
            binding.rcvAccount.adapter = accountAdapter
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAccountBinding.inflate(inflater)
}