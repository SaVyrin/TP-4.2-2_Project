package ru.surfstudio.standard.i_ipu.entity

import com.google.gson.annotations.SerializedName

data class SendIpuObj(
    @SerializedName("ipuId") val ipuId: String,
    @SerializedName("value") val value: String
)