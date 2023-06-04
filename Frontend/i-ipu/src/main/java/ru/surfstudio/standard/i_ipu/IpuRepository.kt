package ru.surfstudio.standard.i_ipu

import io.reactivex.Completable
import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.i_ipu.entity.SendIpuObj
import ru.surfstudio.standard.i_ipu.entity.SendIpuRequest
import ru.surfstudio.standard.i_network.network.transformCollection
import ru.surfstudio.standard.i_network.service.BaseNetworkService
import javax.inject.Inject

@PerApplication
class IpuRepository @Inject constructor(
    private val ipuApi: IpuApi
) : BaseNetworkService() {

    fun getCurrentIpu(personalAccount: String): Single<List<Ipu>> {
        return ipuApi.getCurrentIpu(personalAccount).transformCollection()
    }

    fun sendIpu(ipu: List<Ipu>): Completable {
        val sendIpuObjects = ipu.map { SendIpuObj(it.id.toString(), it.value) }
        val request = SendIpuRequest(sendIpuObjects)
        return ipuApi.sendIpu(request)
    }
}

