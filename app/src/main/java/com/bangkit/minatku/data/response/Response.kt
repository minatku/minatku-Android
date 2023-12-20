package com.bangkit.minatku.data.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("user_data")
	val userData: UserData,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String
)

data class UserData(

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("foto_profil")
	val fotoProfil: String,

	@field:SerializedName("is_premium")
	val isPremium: Any? = null,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("create_at")
	val createAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String,

	@field:SerializedName("major_predict")
	val majorPredict: List<MajorPredictItem?>? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("no_telepon")
	val noTelepon: String
)

data class MajorPredictItem(

	@field:SerializedName("top_1")
	val top1: String? = null,

	@field:SerializedName("update_at")
	val updateAt: Any? = null,

	@field:SerializedName("top_4")
	val top4: String? = null,

	@field:SerializedName("create_at")
	val createAt: String? = null,

	@field:SerializedName("top_5")
	val top5: String? = null,

	@field:SerializedName("top_2")
	val top2: String? = null,

	@field:SerializedName("top_3")
	val top3: String? = null
)
