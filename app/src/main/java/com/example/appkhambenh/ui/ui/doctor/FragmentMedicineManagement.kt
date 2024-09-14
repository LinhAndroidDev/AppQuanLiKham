package com.example.appkhambenh.ui.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import com.example.appkhambenh.databinding.FragmentMedicineManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.model.MedicineModel
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.DetailMedicineAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.NameMedicineAdapter

class FragmentMedicineManagement : BaseFragment<EmptyViewModel, FragmentMedicineManagementBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()

        showLoading()
        binding.root.postDelayed(1000L) {
            dismissLoading()
            initListMedicine()
        }
    }

    private fun initListMedicine() {
        val medicines = arrayListOf<MedicineModel>()
        medicines.add(MedicineModel(id = 3, name = "Panadol", inventoryNumber = 15, updateDay = "2022/06/04"))
        medicines.add(MedicineModel(id = 3, name = "Panadol", inventoryNumber = 15, updateDay = "2022/06/04"))
        medicines.add(MedicineModel(id = 3, name = "Panadol", inventoryNumber = 15, updateDay = "2022/06/04"))
        medicines.add(MedicineModel(id = 3, name = "Panadol", inventoryNumber = 15, updateDay = "2022/06/04"))
        val nameMedicineAdapter = NameMedicineAdapter()
        nameMedicineAdapter.items = medicines
        binding.rcvNameMedicine.adapter = nameMedicineAdapter

        val detailMedicineAdapter = DetailMedicineAdapter()
        detailMedicineAdapter.items = medicines
        binding.rcvInfoMedicine.adapter = detailMedicineAdapter
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