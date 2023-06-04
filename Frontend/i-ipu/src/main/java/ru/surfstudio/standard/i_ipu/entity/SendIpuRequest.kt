package ru.surfstudio.standard.i_ipu.entity

import com.google.gson.annotations.SerializedName

data class SendIpuRequest(
    @SerializedName("indications") val indications: List<SendIpuObj>,
)