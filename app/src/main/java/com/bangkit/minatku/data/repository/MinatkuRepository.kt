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
import com.bangkit.minatku.data.response.UpdatePP
import com.bangkit.minatku.data.response.UserUpdate
import com.bangkit.minatku.data.retrofit.ApiConfig
import com.bangkit.minatku.data.retrofit.ApiService
import com.bangkit.minatku.data.retrofit.AssessmentRequest
import com.bangkit.minatku.data.retrofit.LoginRequest
import com.bangkit.minatku.data.retrofit.RegisterRequest
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
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

    suspend fun updatepp(
        id: Int,
        multipartBody: MultipartBody.Part
    ): Hasil<UpdatePP> {
        Hasil.Loading
        return try {
            val response = apiService.postpp(id,multipartBody)

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
        tanggal_lahir: String,
        gender: String,
        no_telp: String,
        lokasi: String
    ): Hasil<UserUpdate> {
        Hasil.Loading
        return try {
            val session = UpdateUser(
                username,
                nama_lengkap,
                tanggal_lahir,
                gender,
                no_telp,
                lokasi
            )
            val response = apiService.updateUser(id, session)

            if (response.error == true) {
                Hasil.Error(response.message ?: "Unknown error")
            } else {
                Hasil.Success(response)
            }

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "Unknown error"
            Hasil.Error(errorMessage)
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
        return try {
            val response = apiService.user(id)
            if (response.error == true) {
                Hasil.Error(response.message ?: "Unknown error")
            } else {
                val userData = response.userData

                val session = UserDetail(
                    name = userData?.username.orEmpty(),
                    tgl_lahir = userData?.tanggalLahir.orEmpty(),
                    gender = userData?.gender.orEmpty(),
                    lokasi = userData?.lokasi.orEmpty(),
                    fotoPP = userData?.fotoProfil.orEmpty(),
                    name_lengkap = userData?.namaLengkap.orEmpty(),
                    no_telp = userData?.noTelepon.orEmpty()
                )
                saveDetail(session)
                Hasil.Success(response)
            }

        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "Unknown error"
            Hasil.Error(errorMessage)
        }
    }

    suspend fun submitAssessment(answers: List<Int>): Hasil<Boolean> {
        return try {
            // Create an instance of the data class representing the JSON structure
            val assessmentRequest = AssessmentRequest(input = answers)

            // Make the API call with the data class object
            val response = apiService.submitAssessment(assessmentRequest)

            if (response.error == true) {
                Hasil.Error(response.message ?: "Unknown error")
            } else {
                Hasil.Success(true)
            }
        } catch (e: Exception) {
            Hasil.Error(e.message ?: "Unknown error")
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