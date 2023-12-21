package com.bangkit.minatku.data.pref

import com.bangkit.minatku.data.response.MajorPredictItem
import kotlin.collections.List

data class UserModel(
    val name: String,
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val userId: Int
)

data class UserDetail(
    val name: String,
    val tgl_lahir: String,
    val gender: String,
    val lokasi: String,
    val fotoPP: String,
    val name_lengkap: String,
    val no_telp: String,
)

data class Top(
    val top1: String,
    val top2: String,
    val top3: String,
    val top4: String,
    val top5: String
)