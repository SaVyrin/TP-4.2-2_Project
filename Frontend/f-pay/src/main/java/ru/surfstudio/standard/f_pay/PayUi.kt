package ru.surfstudio.standard.f_pay

import ru.surfstudio.standard.domain.entity.Ipu

data class PayUi(
    val ipu: Ipu,
    val payAmount: String
)