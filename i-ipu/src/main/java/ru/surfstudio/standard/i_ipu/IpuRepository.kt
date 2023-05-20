package ru.surfstudio.standard.i_ipu

import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.i_ipu.entity.CurrentIpuRequest
import ru.surfstudio.standard.i_ipu.entity.SendIpuRequest
import ru.surfstudio.standard.i_ipu.entity.SendIpuResponse
import ru.surfstudio.standard.i_network.network.transformCollection
import ru.surfstudio.standard.i_network.service.BaseNetworkService
import javax.inject.Inject

@PerApplication
class IpuRepository @Inject constructor(
    private val ipuApi: IpuApi
) : BaseNetworkService() {

    fun getCurrentIpu(personalAccount: String): Single<List<Ipu>> {
        return ipuApi.getCurrentIpu(CurrentIpuRequest(personalAccount)).transformCollection()
    }

    fun sendIpu(ipu: List<Ipu>): Single<SendIpuResponse> {
        val ipuRequest = ipu.map { SendIpuRequest(it.id.toString(), it.value) }
        return ipuApi.sendIpu(ipuRequest)
    }
}

