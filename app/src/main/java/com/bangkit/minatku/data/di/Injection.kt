package com.bangkit.minatku.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.minatku.data.pref.UserPreference
import com.bangkit.minatku.data.repository.MinatkuRepository
import com.bangkit.minatku.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")
object Injection {
    fun provideRepository(context: Context): MinatkuRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return MinatkuRepository.getInstance(apiService, pref)
    }
}