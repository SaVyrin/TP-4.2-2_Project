package ru.surfstudio.standard.i_ipu.entity

import com.google.gson.annotations.SerializedName

data class CurrentIpuRequest(
    @SerializedName("personalAccount") val personalAccount: String
)