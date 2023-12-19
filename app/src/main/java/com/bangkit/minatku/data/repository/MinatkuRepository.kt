package com.bangkit.minatku.data.repository

import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.response.ErrorResponse
import com.bangkit.minatku.data.response.RegisterResponse
import com.bangkit.minatku.data.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class MinatkuRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun register(name: String, email: String, nama_lengkap:String ,password: String): Hasil<RegisterResponse> {
        Hasil.Loading
        return try {
            // Lakukan pemanggilan API untuk registrasi
            val response = apiService.register(name, email, nama_lengkap, password)

            if (response.error == true) {
                Hasil.Error(response.message ?: "Unknown error")
            } else {
                Hasil.Success(response)
            }
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Hasil.Error("Network error: ${e.message}")
        }
    }
}