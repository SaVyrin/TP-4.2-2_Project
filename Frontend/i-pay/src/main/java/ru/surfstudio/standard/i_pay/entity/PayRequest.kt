package ru.surfstudio.standard.i_pay.entity

import com.google.gson.annotations.SerializedName

data class PayRequest(
    @SerializedName("personalAccount") val personalAccount: String
)