package com.bangkit.minatku.data.pref

data class UpdateUser(
    val username: String,
    val nama_lengkap: String,
    val tanggal_lahir: String,
    val gender: String,
    val no_telepon: String,
    val lokasi: String
)