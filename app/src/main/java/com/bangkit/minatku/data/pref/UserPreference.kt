package com.bangkit.minatku.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.minatku.data.response.MajorPredictItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    suspend fun saveDetail(user: UserDetail) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[TGL_LAHIR] = user.tgl_lahir
            preferences[NAMA_LENGKAP] = user.name_lengkap
            preferences[GENDER] = user.gender
            preferences[LOKASI] = user.lokasi
            preferences[PP] = user.fotoPP
            preferences[TELP] = user.no_telp
        }
    }

    suspend fun saveTop(user: Top) {
        dataStore.edit { preferences ->
            preferences[TOP1] = user.top1
            preferences[TOP2] = user.top2
            preferences[TOP3] = user.top3
            preferences[TOP4] = user.top3
            preferences[TOP5] = user.top5
        }
    }

    fun getDetail(): Flow<UserDetail> {
        return dataStore.data.map { preferences ->
            UserDetail(
                preferences[NAME_KEY] ?: "",
                preferences[TGL_LAHIR] ?: "",
                preferences[GENDER] ?: "",
                preferences[LOKASI] ?: "",
                preferences[PP] ?: "",
                preferences[NAMA_LENGKAP] ?: "",
                preferences[TELP] ?: ""
            )
        }
    }

    fun getTop(): Flow<Top> {
        return dataStore.data.map { preferences ->
            Top(
                preferences[TOP1] ?: "",
                preferences[TOP2] ?: "",
                preferences[TOP3] ?: "",
                preferences[TOP4] ?: "",
                preferences[TOP5] ?: ""
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
        private val TELP = stringPreferencesKey("Telp")
        private val TOP1 = stringPreferencesKey("top1")
        private val TOP2 = stringPreferencesKey("top2")
        private val TOP3 = stringPreferencesKey("top3")
        private val TOP4 = stringPreferencesKey("top4")
        private val TOP5 = stringPreferencesKey("top5")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return UserPreference(dataStore)
        }
    }
}
