package com.bangkit.minatku.ui.editprofil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.response.UserUpdate
import kotlinx.coroutines.launch

class EditProfileViewmodel(private val storyRepository: MinatkuRepository) : ViewModel() {
    private val _updateResult = MutableLiveData<Hasil<UserUpdate>>()
    val updateResult: LiveData<Hasil<UserUpdate>> get() = _updateResult

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
}