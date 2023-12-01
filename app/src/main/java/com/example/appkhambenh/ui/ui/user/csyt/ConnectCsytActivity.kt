package com.example.appkhambenh.ui.ui.user.csyt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityConnectCsytBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Csyt
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.csyt.adapter.CsytAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnectCsytActivity : BaseActivity<EmptyViewModel, ActivityConnectCsytBinding>() {

    private val listCsyt by lazy { arrayListOf<Csyt>() }
    private val csytAdapter by lazy { CsytAdapter(listCsyt) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initUi() {

        binding.headerCsyt.visibleSearch()
        binding.headerCsyt.setTitle(getString(R.string.csyt))
        binding.headerCsyt.setHint(getString(R.string.search_csyt))

        GlobalScope.launch(Dispatchers.Main) {
            delay(300L)
            dataCsyt()
        }

        csytAdapter.onClickItem = {
            if(it) {
                val intent = Intent(this@ConnectCsytActivity, InfoCsytActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun dataCsyt() {
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

        binding.rcvCsyt.adapter = csytAdapter
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityConnectCsytBinding.inflate(inflater)
}