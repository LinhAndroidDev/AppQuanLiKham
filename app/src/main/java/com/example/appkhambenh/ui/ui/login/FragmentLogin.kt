package com.example.appkhambenh.ui.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentLoginBinding
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogStudent
import com.example.appkhambenh.ui.ui.doctor.DoctorActivity
import com.example.appkhambenh.ui.ui.doctor.FragmentTreatmentManagement
import com.example.appkhambenh.ui.ui.register.FragmentRegister
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.apache.poi.ss.usermodel.WorkbookFactory

@Parcelize
data class Student(
    val id: Int,
    val name: String,
    val age: Int,
    val address: String
) : Parcelable

@Suppress("DEPRECATION")
@AndroidEntryPoint
class FragmentLogin : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    companion object {
        const val LIST_STUDENT = "LIST_STUDENT"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.intent?.let { handleIntent(it) }
        initUi()
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEND) {
            val uri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            if (uri != null) {
                val students = readExcelFile(requireActivity(), uri)
                val dialogStudent = DialogStudent()
                dialogStudent.show(parentFragmentManager, "")
                val bundle = Bundle()
                bundle.putParcelableArrayList(LIST_STUDENT, students)
                dialogStudent.arguments = bundle
            }
        }
    }

    private fun readExcelFile(context: Context, uri: Uri): ArrayList<Student> {
        val students = mutableListOf<Student>()
        val inputStream = context.contentResolver.openInputStream(uri)
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)

        for (row in sheet) {
            if (row.rowNum == 0) continue  // Bỏ qua hàng tiêu đề
            val id = row.getCell(0).numericCellValue.toInt()
            val name = row.getCell(1).stringCellValue
            val age = row.getCell(2).numericCellValue.toInt()
            val address = row.getCell(3).stringCellValue
            students.add(Student(id, name, age, address))
        }

        workbook.close()
        inputStream?.close()
        return students as ArrayList<Student>
    }

    override fun bindData() {
        super.bindData()

        viewModel.loginSuccessLiveData.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                if (binding.checkForgetPassword.isChecked) {
                    saveAccount(
                        email = binding.edtAccount.text.toString(),
                        password = binding.edtPassword.text.toString(),
                        isForget = true
                    )
                } else if (!binding.checkForgetPassword.isChecked) {
                    saveAccount("", "", false)
                }

                sharePrefer.saveCheckLogin(true)

                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.doctorLoginSuccess.collect { isSuccess->
                 if(isSuccess) {
                    if (binding.checkForgetPassword.isChecked) {
                        saveAccount(
                            email = binding.edtAccount.text.toString(),
                            password = binding.edtPassword.text.toString(),
                            isForget = true
                        )
                    } else if (!binding.checkForgetPassword.isChecked) {
                        saveAccount("", "", false)
                    }

                    sharePrefer.saveCheckLogin(true)

                    val intent = Intent(requireActivity(), DoctorActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun saveAccount(email: String, password: String, isForget: Boolean) {
        sharePrefer.saveEmail(email)
        sharePrefer.savePassword(password)
        sharePrefer.saveRememberPassword(isForget)
    }

    @SuppressLint("CommitTransaction", "ClickableViewAccessibility")
    private fun initUi() {
        checkSaveAccount()

        binding.showPassword.setOnClickListener {
            if (binding.edtPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.showPassword.setBackgroundResource(R.drawable.icon_show_password_grey)
                binding.edtPassword.transformationMethod = null
            } else if (binding.edtPassword.transformationMethod == null) {
                binding.showPassword.setBackgroundResource(R.drawable.icon_hint_grey)
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        binding.loginDoctor.setOnClickListener {
            val email = binding.edtAccount.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.loginDoctor(email, password)
        }

        binding.login.setOnClickListener {
//            val email = binding.edtAccount.text.toString()
//            val password = binding.edtPassword.text.toString()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                setNotification(R.color.txt_green, R.string.enter_enough_info)
//            } else if (!validateEmail(email)) {
//                setNotification(R.color.txt_red, R.string.fail_email)
//            } else if (!validatePassword(password)) {
//                setNotification(R.color.txt_red, R.string.fail_password)
//            } else {
//                binding.notificationLogin.visibility = View.GONE
//
//                lifecycleScope.launch(Dispatchers.Main) {
//                    viewModel.requestLoginUser(
//                        context = requireActivity(),
//                        loginModel = LoginModel(email, password)
//                    )
//                }
//            }
            if (binding.checkForgetPassword.isChecked) {
                saveAccount(
                    email = binding.edtAccount.text.toString(),
                    password = binding.edtPassword.text.toString(),
                    isForget = true
                )
            } else if (!binding.checkForgetPassword.isChecked) {
                saveAccount("", "", false)
            }

            sharePrefer.saveCheckLogin(true)

            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.register.setOnClickListener {
            val fragmentRegister = FragmentRegister()
            val fm: FragmentTransaction =
                parentFragmentManager.beginTransaction()
            fm.replace(R.id.changeIdLogin, fragmentRegister).addToBackStack(null).commit()
        }

        binding.layoutLogin.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            false
        }
    }

    private fun checkSaveAccount() {
        binding.edtAccount.setText(sharePrefer.getEmail())
        binding.edtPassword.setText(sharePrefer.getPassword())
        binding.checkForgetPassword.isChecked = true
    }

    private fun setNotification(color: Int, string: Int) {
        val shake: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_shake)
        binding.notificationLogin.text = resources.getString(string)
        binding.notificationLogin.setTextColor(resources.getColor(color))
        binding.notificationLogin.visibility = View.VISIBLE
        binding.notificationLogin.startAnimation(shake)
    }

    override fun onFragmentBack(): Boolean {
        return true
    }
}