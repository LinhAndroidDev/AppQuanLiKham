package com.example.appkhambenh.ui.ui.user.service

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.Specialist
import com.example.appkhambenh.ui.data.remote.repository.SpecialistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class FragmentSpecialistViewModel @Inject constructor(private val repository: SpecialistRepository) :
    BaseViewModel() {
    private val _specialists: MutableStateFlow<ArrayList<Specialist>?> = MutableStateFlow(null)
    val specialists = _specialists.asSharedFlow()

    suspend fun getListSpecialist() {
        loading.postValue(true)
        try {
            loading.postValue(false)
            repository.getListSpecialist().let { response ->
                if(response.isSuccessful) {
                    response.body().let {
                        _specialists.value = it?.data
                    }
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}