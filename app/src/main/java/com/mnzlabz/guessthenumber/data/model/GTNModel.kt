package com.mnzlabz.guessthenumber.data.model

import com.google.gson.annotations.SerializedName

data class GTNModel(
    @SerializedName("value")
    val value: Int?,
    val isSuccessful: Boolean,
    val statusCode: Int,
    val message: String
)