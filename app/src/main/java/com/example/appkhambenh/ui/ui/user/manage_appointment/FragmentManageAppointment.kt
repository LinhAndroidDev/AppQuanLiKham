package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentManageAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.appointment.register.FragmentAppointment
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ManageAppointmentAdapter
import com.example.appkhambenh.ui.utils.SharePreferenceRepository
import com.example.appkhambenh.ui.utils.setStyleTextAtPosition

class FragmentManageAppointment : BaseFragment<ManageAppointmentViewModel, FragmentManageAppointmentBinding>() {

    lateinit var adapterManageAppointment: ManageAppointmentAdapter

    companion object {
        const val REGISTER_CHECKING = "REGISTER_CHECKING"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.getListAppointment(
            convertToRequestBody(sharePrefer.getUserId().toString())
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

        binding.headerManageAppoint.setTitle(getString(R.string.manage_appointment))
        binding.headerManageAppoint.visibleSearch()

        adapterManageAppointment =
            ManageAppointmentAdapter(null, requireActivity())

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingData.visibility = if(isLoading) View.VISIBLE else View.GONE
        }

        viewModel.listAppointmentLiveData.observe(viewLifecycleOwner) {
            adapterManageAppointment =
                ManageAppointmentAdapter(it, requireActivity())
            val linear = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.rcvManageAppointment.layoutManager = linear
            binding.rcvManageAppointment.adapter = adapterManageAppointment

            adapterManageAppointment.isClickEditAppoint = { registerChecking ->
                val fragmentAppoint = FragmentAppointment()
                val fm = requireActivity().supportFragmentManager.beginTransaction()
                fm.hide(requireActivity().supportFragmentManager.findFragmentByTag("FragmentManageAppointment")!!)
                    .add(R.id.changeIdManageAppointment, fragmentAppoint)
                    .addToBackStack(null).commit()
                val bundle = Bundle()
                bundle.putSerializable(REGISTER_CHECKING, registerChecking)
                fragmentAppoint.arguments = bundle
            }

            adapterManageAppointment.isCancelAppoint = { registerChecking ->

                //set style text notification
                val notification = "Lịch hẹn ${registerChecking!!.date} lúc ${registerChecking.hour} của bạn sẽ được xoá khỏi danh sách"
                val spannable = SpannableString(notification)
                setStyleTextAtPosition(notification, registerChecking.date!!, StyleSpan(Typeface.BOLD_ITALIC), spannable)
                setStyleTextAtPosition(notification, registerChecking.hour!!, StyleSpan(Typeface.BOLD_ITALIC), spannable)

                val alertDialog : AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                alertDialog.setTitle("Xác nhận xoá lịch hẹn")
                alertDialog.setIcon(R.mipmap.ic_launcher)
                alertDialog.setMessage(spannable)
                alertDialog.setPositiveButton("Đồng ý") { _, _ ->
                    viewModel.deleteAppoint(
                        convertToRequestBody("2"),
                        convertToRequestBody(registerChecking.date),
                        convertToRequestBody(registerChecking.hour),
                        convertToRequestBody("0")
                    )
                    object : CountDownTimer(300, 300) {
                        override fun onTick(p0: Long) {

                        }

                        @SuppressLint("NotifyDataSetChanged")
                        override fun onFinish() {
                            if(viewModel.deleteSuccessful){
                                show("Bạn đã xoá thành công lịch hẹn ${registerChecking.date} lúc ${registerChecking.hour}")
                                viewModel.getListAppointment(
                                    convertToRequestBody(sharePrefer.getUserId().toString())
                                )
                                adapterManageAppointment.notifyDataSetChanged()
                            }
                        }
                    }.start()
                }
                alertDialog.setNegativeButton("Không") { _, _ -> }
                alertDialog.show()
            }
        }

        binding.headerManageAppoint.searchItem = {
            adapterManageAppointment.filter.filter(it)
        }

        binding.headerManageAppoint.isRevert = {
            if(it) adapterManageAppointment.revertAppoint()
        }

        binding.headerManageAppoint.visibleSetting()

        binding.layoutHideKeyboard.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            binding.headerManageAppoint.clearFocusSearch()
            false
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentManageAppointmentBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }
}