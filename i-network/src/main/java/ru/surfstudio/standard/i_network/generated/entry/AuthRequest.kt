package ru.surfstudio.standard.i_network.generated.entry

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
)