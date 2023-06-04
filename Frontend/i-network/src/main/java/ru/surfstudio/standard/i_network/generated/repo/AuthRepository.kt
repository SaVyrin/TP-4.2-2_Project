package ru.surfstudio.standard.i_network.generated.repo

import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.i_network.generated.api.AuthApi
import ru.surfstudio.standard.i_network.generated.entry.AuthRequest
import ru.surfstudio.standard.i_network.network.transform
import ru.surfstudio.standard.i_network.service.BaseNetworkService
import javax.inject.Inject

private const val CODE_FORMAT = "%s:%s"

/**
 * Сервис, отвечающий за авторизацию и регистрацию пользователя
 */
@PerApplication
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : BaseNetworkService() {

    /**
     * Отсылка номера телефона для получения кода авторизации
     */
    fun auth(login: String, password: String): Single<UserInfo> {
        return authApi.auth(AuthRequest(login, password)).transform()
    }
}

