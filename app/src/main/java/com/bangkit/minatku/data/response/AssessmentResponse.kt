package com.bangkit.minatku.data.response

import com.google.gson.annotations.SerializedName

data class AsessmentResponse(

	@field:SerializedName("pertanyaan_data")
	val pertanyaanData: List<PertanyaanDataItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PertanyaanDataItem(

	@field:SerializedName("kelas_pertanyaan")
	val kelasPertanyaan: String? = null,

	@field:SerializedName("kode")
	val kode: String? = null,

	@field:SerializedName("id_pertanyaan")
	val idPertanyaan: Int? = null,

	@field:SerializedName("isi_pertanyaan")
	val isiPertanyaan: String? = null
)
