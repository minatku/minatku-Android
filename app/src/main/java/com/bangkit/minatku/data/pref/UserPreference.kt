package com.bangkit.minatku.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
            preferences[USER_ID] = user.userId.toString() // Convert to String
        }
    }

    suspend fun saveDetail(user: UserDetail){
        dataStore.edit { preferences ->
            preferences[TGL_LAHIR] = user.tgl_lahir.toString()
            preferences[NAMA_LENGKAP] = user.name_lengkap
            preferences[GENDER] = user.gender
            preferences[LOKASI] = user.lokasi
            preferences[PP] = user.fotoPP
            preferences[TELP] = user.no_telp.toString()
        }
    }

    fun getDetail(): Flow<UserDetail>{
        return dataStore.data.map { preferences->
            UserDetail(
            preferences[TGL_LAHIR]?: "",
            preferences[GENDER]?: "",
            preferences[LOKASI]?: "",
            preferences[PP]?: "",
            preferences[NAMA_LENGKAP]?: "",
            preferences[USER_ID]?:""
            )
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
                preferences[USER_ID]?.toInt() ?: 0, // Convert to Int
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val USER_ID = stringPreferencesKey("userId")
        private val NAMA_LENGKAP = stringPreferencesKey("nama")
        private val TGL_LAHIR = stringPreferencesKey("tanggal_lahir")
        private val GENDER = stringPreferencesKey("gender")
        private val LOKASI = stringPreferencesKey("lokasi")
        private val PP = stringPreferencesKey("FotoPP")
        private val TELP = stringPreferencesKey("Telp")// Change to stringPreferencesKey

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return UserPreference(dataStore)
        }
    }
}
