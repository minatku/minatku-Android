package com.bangkit.minatku.ui.editprofil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.response.UpdatePP
import com.bangkit.minatku.data.response.UserUpdate
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditProfileViewmodel(private val storyRepository: MinatkuRepository) : ViewModel() {
    private val _updateResult = MutableLiveData<Hasil<UserUpdate>>()
    val updateResult: LiveData<Hasil<UserUpdate>> get() = _updateResult

    private val _updatepp = MutableLiveData<Hasil<UpdatePP>>()
    val updatePP: LiveData<Hasil<UpdatePP>> get() = _updatepp

    fun update(
        id: Int,
        username: String,
        nama_lengkap: String,
        tgl_lahir: String,
        gender: String,
        no_telp: String,
        lokasi: String
    ) {
        _updateResult.value = Hasil.Loading

        viewModelScope.launch {
            val result = storyRepository.updateUser(id,username, nama_lengkap, tgl_lahir, gender,no_telp,lokasi)
            _updateResult.value = result
        }
    }

    fun updatepp(
        id: Int,
        multipartBody: MultipartBody.Part
    ){
        _updatepp.value = Hasil.Loading
        viewModelScope.launch {
            val result = storyRepository.updatepp(id,multipartBody)
            _updatepp.value = result
        }
    }
}