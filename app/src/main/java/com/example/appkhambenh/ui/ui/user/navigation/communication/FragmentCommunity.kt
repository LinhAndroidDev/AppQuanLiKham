package com.example.appkhambenh.ui.ui.user.navigation.communication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentCommunityBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel

class FragmentCommunity : BaseFragment<EmptyViewModel, FragmentCommunityBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerCommunity.setTitle("Cộng đồng hỏi đáp")

        binding.allCommunity.statusCheckedCommunity()
        binding.mine.statusUnCheckedCommunity()

        initListCommunity()

        binding.allCommunity.setOnClickListener {
            binding.allCommunity.statusCheckedCommunity()
            binding.mine.statusUnCheckedCommunity()
            binding.content.visibility = View.VISIBLE
            binding.noData.visibility = View.GONE
        }

        binding.mine.setOnClickListener {
            binding.allCommunity.statusUnCheckedCommunity()
            binding.mine.statusCheckedCommunity()
            binding.content.visibility = View.GONE
            binding.noData.visibility = View.VISIBLE
        }

        binding.createQuestion.setOnClickListener {
            val intent = Intent(requireActivity(), CreateQuestionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initListCommunity() {
        binding.rcvCommunity.adapter = CommunityAdapter(requireActivity())
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentCommunityBinding.inflate(inflater)
}