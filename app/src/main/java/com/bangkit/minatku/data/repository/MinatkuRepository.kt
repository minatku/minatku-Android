package com.bangkit.minatku.data.repository

import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.pref.UpdateUser
import com.bangkit.minatku.data.pref.UserDetail
import com.bangkit.minatku.data.pref.UserModel
import com.bangkit.minatku.data.pref.UserPreference
import com.bangkit.minatku.data.response.AsessmentResponse
import com.bangkit.minatku.data.response.ErrorResponse
import com.bangkit.minatku.data.response.LoginResponse
import com.bangkit.minatku.data.response.RegisterResponse
import com.bangkit.minatku.data.response.Response
import com.bangkit.minatku.data.response.UserUpdate
import com.bangkit.minatku.data.retrofit.ApiConfig
import com.bangkit.minatku.data.retrofit.ApiService
import com.bangkit.minatku.data.retrofit.LoginRequest
import com.bangkit.minatku.data.retrofit.RegisterRequest
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class MinatkuRepository private constructor(
    private val apiService: ApiService,

    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun saveDetail(user: UserDetail) {
        userPreference.saveDetail(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getDetail(): Flow<UserDetail> {
        return userPreference.getDetail()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(
        username: String,
        nama_lengkap: String,
        email: String,
        password: String
    ): Hasil<RegisterResponse> {
        Hasil.Loading
        return try {
            // Lakukan pemanggilan API untuk registrasi
            val request = RegisterRequest(email, username, nama_lengkap, password)
            val response = apiService.register(request)

            if (response.error == true) {
                Hasil.Error(response.message ?: "Unknown error")
            } else {
                Hasil.Success(response)
            }
        } catch (e: HttpException) {
            // Handle exception
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Hasil.Error(errorMessage.toString())
        }
    }

    suspend fun updateUser(
        id: Int,
        username: String,
        nama_lengkap: String,
        tgl_lahir: String,
        gender: String,
        no_telp: String,
        lokasi: String
    ): Hasil<UserUpdate> {
        Hasil.Loading
        return try {
            val session = UpdateUser(username, nama_lengkap, tgl_lahir, gender, no_telp, lokasi)
            val response = apiService.updateUser(id,session)

            if (response.error == true) {
                Hasil.Error(response.message ?: "Unknown error")
            } else {
                Hasil.Success(response)
            }

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Hasil.Error(errorMessage.toString())
        }
    }

    suspend fun login(email: String, password: String): Hasil<LoginResponse> {
        Hasil.Loading
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)

            if (response.error == true) {
                Hasil.Error(response.message)
            } else {
                val session = UserModel(
                    name = response.loginResult.username,
                    email = email,
                    token = response.loginResult.token,
                    isLogin = true,
                    userId = response.loginResult.userId
                )
                saveSession(session)
                ApiConfig.token = response.loginResult.token
                Hasil.Success(response)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Hasil.Error(errorMessage.toString())
        }
    }

    suspend fun detail(id: Int): Hasil<Response> {
        Hasil.Loading
        return try {
            val response = apiService.user(id)
            if (response.error == true) {
                Hasil.Error(response.message)
            } else {
                val session = UserDetail(
                    tgl_lahir = response.userData.tanggalLahir,
                    gender = response.userData.gender,
                    lokasi = response.userData.lokasi,
                    fotoPP = response.userData.fotoProfil,
                    name_lengkap = response.userData.namaLengkap,
                    no_telp = response.userData.noTelepon
                )
                saveDetail(session)
                Hasil.Success(response)
            }

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Hasil.Error(errorMessage.toString())
        }
    }

    suspend fun submitAssessment(answers: List<Int>): Boolean {
        return try {
            val response = apiService.submitAssessment(answers)
            response.error == true // Use safe call operator
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getQuestions(): AsessmentResponse {
        return try {
            apiService.getQuestions()
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        @Volatile
        private var instance: MinatkuRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): MinatkuRepository = MinatkuRepository(apiService, userPreference)
    }
}