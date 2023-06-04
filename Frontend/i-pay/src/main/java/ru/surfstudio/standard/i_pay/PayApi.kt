package ru.surfstudio.standard.i_pay

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.surfstudio.standard.i_pay.entity.PayRequest
import ru.surfstudio.standard.i_pay.entity.PaymentsResponse
import ru.surfstudio.standard.i_network.generated.entry.*
import ru.surfstudio.standard.i_network.generated.urls.ServerUrls

/**
 * Api для оплаты
 */
interface PayApi {

    @GET(ServerUrls.PAYMENTS)
    fun getPayments(@Query("personalAccount") personalAccount: String): Single<List<PaymentsResponse>>

    @GET(ServerUrls.PAYMENT_EXPECTED)
    fun getExpectedPayment(@Query("personalAccount") personalAccount: String): Single<Int>

    @POST(ServerUrls.PAY)
    fun pay(@Body request: PayRequest): Completable
}