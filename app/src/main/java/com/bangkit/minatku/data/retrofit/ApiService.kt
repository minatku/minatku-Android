package com.bangkit.minatku.data.retrofit

import com.bangkit.minatku.data.response.AsessmentResponse
import com.bangkit.minatku.data.response.LoginResponse
import com.bangkit.minatku.data.response.MajorPredictResponse
import com.bangkit.minatku.data.response.RegisterResponse
import com.bangkit.minatku.data.response.Response
import com.bangkit.minatku.data.response.UserData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("Auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("Auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("Pertanyaan/pertanyaan")
    suspend fun getQuestions(): AsessmentResponse
    @POST("Major_Predict/predict")
    suspend fun submitAssessment(@Body answers: List<Int>): MajorPredictResponse

    @GET("User/user/{user_id}")
    suspend fun user(
        @Path("user_id") id: Int
    ): Response


}
