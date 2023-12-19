package com.bangkit.minatku.data.retrofit

import com.bangkit.minatku.data.response.LoginResponse
import com.bangkit.minatku.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("Auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("Auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
