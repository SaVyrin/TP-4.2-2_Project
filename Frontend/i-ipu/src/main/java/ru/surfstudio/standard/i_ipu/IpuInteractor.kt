package ru.surfstudio.standard.i_ipu

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Single
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.i_network.network.BaseNetworkInteractor
import javax.inject.Inject

/**
 * Интерактор, отвечающий за ИПУ
 */
@PerApplication
@SuppressLint("CheckResult")
class IpuInteractor @Inject constructor(
    connectionQualityProvider: ConnectionProvider,
    private val ipuRepository: IpuRepository
) : BaseNetworkInteractor(connectionQualityProvider) {

    fun getCurrentIpu(personalAccount: String): Single<List<Ipu>> {
        return ipuRepository.getCurrentIpu(personalAccount).map { list -> list.sortedBy { it.id } }
    }

    fun sendIpu(ipu: List<Ipu>): Completable {
        return ipuRepository.sendIpu(ipu)
    }
}