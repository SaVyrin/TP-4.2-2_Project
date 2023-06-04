package ru.surfstudio.standard.i_pay

import ru.surfstudio.standard.i_pay.entity.LastPayments

interface PaymentsStorage {

    var lastPayments: LastPayments?

    fun clear()
}