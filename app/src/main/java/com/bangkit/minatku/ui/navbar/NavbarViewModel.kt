package com.bangkit.minatku.ui.navbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.minatku.data.pref.UserModel
import com.bangkit.minatku.data.repository.MinatkuRepository

class NavbarViewModel(private val repository: MinatkuRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    /*fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }*/
}