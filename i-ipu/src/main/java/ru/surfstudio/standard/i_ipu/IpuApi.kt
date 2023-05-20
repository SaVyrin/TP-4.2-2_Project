package ru.surfstudio.standard.i_ipu

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.surfstudio.standard.i_ipu.entity.CurrentIpuResponse
import ru.surfstudio.standard.i_ipu.entity.SendIpuRequest
import ru.surfstudio.standard.i_ipu.entity.SendIpuResponse
import ru.surfstudio.standard.i_network.generated.entry.*
import ru.surfstudio.standard.i_network.generated.urls.ServerUrls

/**
 * Api для ИПУ
 */
interface IpuApi {

    @GET(ServerUrls.IPU_CURRENT)
    fun getCurrentIpu(@Query("personalAccount") personalAccount: String): Single<List<CurrentIpuResponse>>

    @POST(ServerUrls.IPU_SEND)
    fun sendIpu(@Body request: SendIpuRequest): Single<SendIpuResponse>
}