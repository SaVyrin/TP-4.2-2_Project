package ru.surfstudio.standard.f_auth

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

internal class AuthState

/**
 * State Holder [AuthFragmentView]
 */
@PerScreen
internal class AuthScreenStateHolder @Inject constructor(
) : State<AuthState>(AuthState())

/**
 * Reducer [AuthFragmentView]
 */
@PerScreen
internal class AuthReducer @Inject constructor(
    dependency: BaseReactorDependency
) : BaseReducer<AuthEvent, AuthState>(dependency) {

    override fun reduce(state: AuthState, event: AuthEvent): AuthState {
        return state
    }
}