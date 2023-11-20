package com.example.appkhambenh.ui.ui.user.csyt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentConnectCsytBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.Csyt
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.csyt.adapter.CsytAdapter

class FragmentConnectCsyt : BaseFragment<EmptyViewModel, FragmentConnectCsytBinding>(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        val listCsyt = arrayListOf<Csyt>()
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))
        listCsyt.add(Csyt(0, "Bệnh viện Hữu Nghị Việt Đức", "Số 18 Phủ Doãn, Hàng Bông, Hoàn Kiếm, Thành phố Hà Nội", R.drawable.img_capital, 61824, 5))

        val csytAdapter = CsytAdapter(listCsyt)
        csytAdapter.onClickItem = {
            if(it) {
                val fragmentInfoCsyt = FragmentInfoCsyt()
                val fm = requireActivity().supportFragmentManager.beginTransaction()
                fm.replace(R.id.changeIdCsyt, fragmentInfoCsyt)
                    .addToBackStack(null).commit()
            }
        }
        binding.rcvCsyt.adapter = csytAdapter

        binding.headerCsyt.setHint(getString(R.string.search_csyt))
    }

    override fun onFragmentBack(): Boolean {
        return true
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentConnectCsytBinding.inflate(inflater)
}