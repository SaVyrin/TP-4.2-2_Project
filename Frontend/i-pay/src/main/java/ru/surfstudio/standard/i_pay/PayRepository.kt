package ru.surfstudio.standard.i_pay

import io.reactivex.Completable
import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.Payment
import ru.surfstudio.standard.i_pay.entity.PayRequest
import ru.surfstudio.standard.i_network.network.transformCollection
import ru.surfstudio.standard.i_network.service.BaseNetworkService
import javax.inject.Inject

@PerApplication
class PayRepository @Inject constructor(
    private val payApi: PayApi
) : BaseNetworkService() {

    fun getPayments(personalAccount: String): Single<List<Payment>> {
        return payApi.getPayments(personalAccount).transformCollection()
    }

    fun getExpectedPayment(personalAccount: String): Single<Int> {
        return payApi.getExpectedPayment(personalAccount)
    }

    fun pay(personalAccount: String): Completable {
        return payApi.pay(PayRequest(personalAccount))
    }
}

