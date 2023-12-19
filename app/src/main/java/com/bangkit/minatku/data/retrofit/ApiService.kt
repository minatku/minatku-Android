package com.bangkit.minatku.data.retrofit

import com.bangkit.minatku.data.response.LoginResponse
import com.bangkit.minatku.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("Auth/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}