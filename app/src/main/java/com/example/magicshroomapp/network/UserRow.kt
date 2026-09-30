package com.example.magicshroomapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRow(
    @SerialName("user_id")
    val userId: Int,

    val username: String,

    @SerialName("created_at")
    val createdAt: String? = null
)