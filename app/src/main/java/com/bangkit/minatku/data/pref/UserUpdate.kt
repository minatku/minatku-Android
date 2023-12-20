package com.bangkit.minatku.data.pref

data class UpdateUser(
    val username: String,
    val nama_lengkap: String,
    val tgl_lahir: String,
    val gender: String,
    val no_telp: String,
    val lokasi: String
)