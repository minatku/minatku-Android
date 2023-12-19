package com.bangkit.minatku.ui.signup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.response.RegisterResponse
import kotlinx.coroutines.launch

class SignUpViewModel(private val minatkuRepository: MinatkuRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Hasil<RegisterResponse>>()
    val registrationResult: LiveData<Hasil<RegisterResponse>> get() = _registrationResult

    fun register(name: String, email: String, nama_lengkap:String ,password: String) {
        _registrationResult.value = Hasil.Loading

        viewModelScope.launch {
            val result = minatkuRepository.register(name, email, nama_lengkap, password)
            _registrationResult.value = result
        }
    }
}