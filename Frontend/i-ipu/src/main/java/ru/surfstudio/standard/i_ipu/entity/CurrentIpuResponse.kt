package ru.surfstudio.standard.i_ipu.entity

import com.google.gson.annotations.SerializedName
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.i_network.network.Transformable

data class CurrentIpuResponse(
    @SerializedName("ipuId") val ipuId: Int,
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: String,
    @SerializedName("date") val date: String,
)  : Transformable<Ipu> {

    override fun transform(): Ipu {
        return Ipu(ipuId, type, value, date)
    }
}