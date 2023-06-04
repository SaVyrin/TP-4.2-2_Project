package ru.surfstudio.standard.f_auth

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.ui.provider.resource.ResourceProvider
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.template.f_auth.R
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_auth.AuthEvent.*
import ru.surfstudio.standard.ui.mvi.mappers.RequestMappers
import javax.inject.Inject

internal data class AuthState(
    val login: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val authRequest: RequestUi<UserInfo> = RequestUi()
)

/**
 * State Holder [AuthFragmentView]
 */
@PerScreen
internal class AuthScreenStateHolder @Inject constructor(
) : State<AuthState>(AuthState())


@PerScreen
internal class AuthCommandHolder @Inject constructor() {
    val errorMessage = Command<String>()
}

/**
 * Reducer [AuthFragmentView]
 */
@PerScreen
internal class AuthReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: AuthCommandHolder,
    private val resourceProvider: ResourceProvider
) : BaseReducer<AuthEvent, AuthState>(dependency) {

    override fun reduce(state: AuthState, event: AuthEvent): AuthState {
        return when(event) {
            is Input.LoginTextChanged -> handleLoginTextChanged(state, event)
            is Input.PasswordTextChanged -> handlePasswordTextChanged(state, event)
            is AuthRequest -> handleAuthRequest(state, event)
            else -> state
        }
    }

    private fun handleLoginTextChanged(state: AuthState, event: Input.LoginTextChanged): AuthState {
        return state.copy(login = event.text)
    }

    private fun handlePasswordTextChanged(state: AuthState, event: Input.PasswordTextChanged): AuthState {
        return state.copy(password = event.text)
    }

    private fun handleAuthRequest(state: AuthState, event: AuthRequest): AuthState {
        val request = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.simple())
            .handleError(RequestMappers.error.noInternet(errorHandler))
            .handleError { _, _, _ ->
                val message = resourceProvider.getString(R.string.default_error_message)
                ch.errorMessage.accept(message)
                true
            }
            .build()

        return state.copy(authRequest = request)
    }
}