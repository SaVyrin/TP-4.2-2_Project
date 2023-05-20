package ru.surfstudio.standard.i_ipu

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.i_ipu.entity.SendIpuResponse
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
        return ipuRepository.getCurrentIpu(personalAccount)
    }

    fun sendIpu(ipu: List<Ipu>): Single<SendIpuResponse> {
        return ipuRepository.sendIpu(ipu)
    }
}