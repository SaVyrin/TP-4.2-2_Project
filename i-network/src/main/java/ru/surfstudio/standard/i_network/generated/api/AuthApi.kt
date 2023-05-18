package ru.surfstudio.standard.i_network.generated.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.surfstudio.standard.i_network.generated.entry.*
import ru.surfstudio.standard.i_network.generated.urls.ServerUrls

/**
 * Api для авторизации
 */
interface AuthApi {

    @POST(ServerUrls.AUTH_URL)
    fun auth(@Body request: AuthRequest): Single<AuthResponse>
}