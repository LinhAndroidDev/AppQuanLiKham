package com.example.appkhambenh.ui.ui.csyt

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityConnectCsytBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Csyt
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.csyt.adapter.CsytAdapter

class ConnectCsytActivity : BaseActivity<EmptyViewModel, ActivityConnectCsytBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.rcvCsyt.adapter = csytAdapter

        binding.searchCsyt.setHint("Tìm kiếm cơ sở y tế")

        binding.backCsyt.setOnClickListener { back() }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityConnectCsytBinding.inflate(inflater)
}