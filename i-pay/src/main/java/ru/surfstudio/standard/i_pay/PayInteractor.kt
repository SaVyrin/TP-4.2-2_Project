package ru.surfstudio.standard.i_pay

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Single
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.Payment
import ru.surfstudio.standard.i_network.network.BaseNetworkInteractor
import javax.inject.Inject

/**
 * Интерактор, отвечающий за ИПУ
 */
@PerApplication
@SuppressLint("CheckResult")
class PayInteractor @Inject constructor(
    connectionQualityProvider: ConnectionProvider,
    private val payRepository: PayRepository
) : BaseNetworkInteractor(connectionQualityProvider) {


    fun getPayments(personalAccount: String): Single<List<Payment>> {
        return payRepository.getPayments(personalAccount)
    }

    fun getExpectedPayment(personalAccount: String): Single<Int> {
        return payRepository.getExpectedPayment(personalAccount)
    }

    fun pay(personalAccount: String): Completable {
        return payRepository.pay(personalAccount)
    }
}