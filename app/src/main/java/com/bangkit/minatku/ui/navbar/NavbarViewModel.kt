package com.bangkit.minatku.ui.navbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.pref.Top
import com.bangkit.minatku.data.pref.UserDetail
import com.bangkit.minatku.data.pref.UserModel
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.response.MajorPredictItem
import com.bangkit.minatku.data.response.Response
import kotlinx.coroutines.launch

class NavbarViewModel(private val repository: MinatkuRepository) : ViewModel() {

    private val _detailResult = MutableLiveData<Hasil<Response>>()
    val detailResult: LiveData<Hasil<Response>> get() = _detailResult

    private val _detailTop = MutableLiveData<Hasil<MajorPredictItem>>()
    val detailTop: LiveData<Hasil<MajorPredictItem>> get() = _detailTop

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getDetail(): LiveData<UserDetail> {
        return repository.getDetail().asLiveData()
    }
    fun detail(id: Int){
        _detailResult.value = Hasil.Loading

        viewModelScope.launch {
            val result = repository.detail(id)
            _detailResult.value = result
        }
    }
    fun getTop(): LiveData<Top>{
        return repository.getTop().asLiveData()
    }

//    fun Top(id: Int){
//        _detailTop.value = Hasil.Loading
//
//        viewModelScope.launch {
//            val result = repository.detail(id)
//            _detailTop.value = result
//        }
//    }
}