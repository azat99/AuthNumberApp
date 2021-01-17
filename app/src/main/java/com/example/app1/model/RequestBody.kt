package com.example.app1.model


import com.google.gson.annotations.SerializedName

data class RequestBody(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)