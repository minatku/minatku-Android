package com.bangkit.minatku.data.response

import com.google.gson.annotations.SerializedName

data class MajorPredictResponse(

	@field:SerializedName("prediction_data")
	val predictionData: PredictionData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PredictionData(

	@field:SerializedName("all_prediction_class")
	val allPredictionClass: List<Int?>? = null,

	@field:SerializedName("prediction_class")
	val predictionClass: Int? = null,

	@field:SerializedName("all_prediction_labels")
	val allPredictionLabels: List<String?>? = null,

	@field:SerializedName("prediction_label")
	val predictionLabel: String? = null
)
