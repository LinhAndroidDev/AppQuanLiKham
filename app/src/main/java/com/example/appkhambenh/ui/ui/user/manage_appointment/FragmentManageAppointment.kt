package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
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
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.setStyleTextAtPosition
import com.example.appkhambenh.ui.utils.setTextNotification

class FragmentManageAppointment : BaseFragment<ManageAppointmentViewModel, FragmentManageAppointmentBinding>() {

    lateinit var adapterManageAppointment: ManageAppointmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.getListAppointment(
            convertToRequestBody(id_user.toString())
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

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
                bundle.putSerializable(PreferenceKey.REGISTER_CHECKING, registerChecking)
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

                        override fun onFinish() {
                            if(viewModel.deleteSuccessful){
                                show("Bạn đã xoá thành công lịch hẹn ${registerChecking.date} lúc ${registerChecking.hour}")
                                viewModel.getListAppointment(
                                    convertToRequestBody(id_user.toString())
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

        binding.backManageAppoint.setOnClickListener { back() }

        binding.searchManageAppoint.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.imgDeleteTxt.visibility =
                if(p0!!.trim().isNotEmpty()) View.VISIBLE else View.GONE

                adapterManageAppointment.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.layoutHideKeyboard.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            binding.searchManageAppoint.clearFocus()
            false
        }

        binding.imgDeleteTxt.setOnClickListener {
            binding.searchManageAppoint.setText("")
        }

        binding.settingManageAppoint.setOnClickListener {
            adapterManageAppointment.revertAppoint()
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