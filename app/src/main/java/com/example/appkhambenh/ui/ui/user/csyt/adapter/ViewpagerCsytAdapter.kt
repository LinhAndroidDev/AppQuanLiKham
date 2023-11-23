package com.example.appkhambenh.ui.ui.user.csyt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.appkhambenh.ui.ui.user.csyt.FragmentDoctorCsyt
import com.example.appkhambenh.ui.ui.user.csyt.FragmentServiceCsyt

enum class Page(val title: String, val fragmentClass : Class<out Fragment>) {
    DOCTOR("ĐẶT THEO BÁC SĨ", FragmentDoctorCsyt::class.java),
    SERVICE("ĐẶT THEO DỊCH VỤ", FragmentServiceCsyt::class.java),
}

@Suppress("DEPRECATION")
class ViewpagerCsytAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    private val pages: List<Page> = arrayListOf<Page>().apply {
        addAll(Page.values())
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return pages[position].fragmentClass.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].title
    }
}