package com.bangkit.minatku.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val storyRepository: MinatkuRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Hasil<LoginResponse>>()
    val loginResult: LiveData<Hasil<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.value = Hasil.Loading

        viewModelScope.launch {
            val result = storyRepository.login(email, password)
            _loginResult.value = result
        }
    }
}