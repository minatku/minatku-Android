package com.bangkit.minatku.data.retrofit

import com.bangkit.minatku.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("password") password: String
    ): RegisterResponse
}