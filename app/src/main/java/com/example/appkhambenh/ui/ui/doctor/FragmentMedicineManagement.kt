package com.example.appkhambenh.ui.ui.doctor

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.databinding.FragmentMedicineManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import org.apache.poi.ss.usermodel.WorkbookFactory

class FragmentMedicineManagement : BaseFragment<EmptyViewModel, FragmentMedicineManagementBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    fun readExcelFile(uri: Uri): List<Student> {
//        val students = mutableListOf<Student>()
//        val inputStream = activity?.contentResolver?.openInputStream(uri)
//        val workbook = WorkbookFactory.create(inputStream)
//        val sheet = workbook.getSheetAt(0)
//
//        for (row in sheet) {
//            if (row.rowNum == 0) continue  // Bỏ qua hàng tiêu đề
//            val id = row.getCell(0).numericCellValue.toInt()
//            val name = row.getCell(1).stringCellValue
//            val age = row.getCell(2).numericCellValue.toInt()
//            val address = row.getCell(3).stringCellValue
//            students.add(Student(id, name, age, address))
//        }
//
//        workbook.close()
//        inputStream?.close()
//        return students
//    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMedicineManagementBinding.inflate(inflater)
}