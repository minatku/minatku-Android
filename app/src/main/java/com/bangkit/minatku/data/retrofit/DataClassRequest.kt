package com.bangkit.minatku.data.retrofit

data class RegisterRequest(
    val email: String,
    val username: String,
    val nama_lengkap: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AssessmentRequest(val input: List<Int>)
