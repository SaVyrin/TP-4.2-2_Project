package ru.surfstudio.standard.i_pay.entity

import com.google.gson.annotations.SerializedName
import ru.surfstudio.standard.domain.entity.Payment
import ru.surfstudio.standard.i_network.network.Transformable

class PaymentsResponse(
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: String,
): Transformable<Payment> {

    override fun transform(): Payment {
        return Payment(
            type = type,
            value = value.toInt()
        )
    }
}