package ru.surfstudio.standard.i_pay.entity

import ru.surfstudio.standard.domain.entity.Payment

data class LastPayments(
    val payments: List<Payment>
)