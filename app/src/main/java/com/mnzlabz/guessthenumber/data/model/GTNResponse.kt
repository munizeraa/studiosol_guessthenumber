package com.mnzlabz.guessthenumber.data.model

import com.google.gson.annotations.SerializedName

data class GTNResponse(
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("Error")
    val error: String?,
    @SerializedName("value")
    val value: Int?
)