package com.bangkit.minatku.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.minatku.data.di.Injection
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.ui.assesment.AssessmentViewModel
import com.bangkit.minatku.ui.login.LoginViewModel
import com.bangkit.minatku.ui.navbar.NavbarViewModel
import com.bangkit.minatku.ui.signup.SignUpViewModel

class ViewModelFactory(private val repository: MinatkuRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NavbarViewModel::class.java) -> {
                NavbarViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AssessmentViewModel::class.java) -> {
                AssessmentViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(Injection.provideRepository(context))
        }
    }
}
