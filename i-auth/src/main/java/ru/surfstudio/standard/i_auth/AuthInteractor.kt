package ru.surfstudio.standard.i_auth

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_debug.DebugInteractor
import ru.surfstudio.standard.i_network.generated.repo.AuthRepository
import ru.surfstudio.standard.i_network.network.BaseNetworkInteractor
import ru.surfstudio.standard.i_session.SessionChangedInteractor
import ru.surfstudio.standard.i_user.UserStorage
import javax.inject.Inject

/**
 * Интерактор, отвечающий за авторизацию пользователя
 */
@PerApplication
@SuppressLint("CheckResult")
class AuthInteractor @Inject constructor(
    connectionQualityProvider: ConnectionProvider,
    debugInteractor: DebugInteractor,
    private val authRepository: AuthRepository,
    private val sessionChangedInteractor: SessionChangedInteractor,
    private val userStorage: UserStorage
) : BaseNetworkInteractor(connectionQualityProvider) {

    init {
        debugInteractor.observeNeedClearSession().subscribe {
            sessionChangedInteractor.onForceLogout()
        }
    }


    fun auth(login: String, password: String): Single<UserInfo> {
        return authRepository.auth(login, password).doOnSuccess {
            userStorage.currentUser = it
        }
    }
}