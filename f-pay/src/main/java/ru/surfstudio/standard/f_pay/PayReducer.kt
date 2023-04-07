package ru.surfstudio.standard.f_pay

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

internal class PayState

/**
 * State Holder [PayFragmentView]
 */
@PerScreen
internal class PayScreenStateHolder @Inject constructor(
) : State<PayState>(PayState())

/**
 * Reducer [PayFragmentView]
 */
@PerScreen
internal class PayReducer @Inject constructor(
    dependency: BaseReactorDependency
) : BaseReducer<PayEvent, PayState>(dependency) {

    override fun reduce(state: PayState, event: PayEvent): PayState {
        return state
    }
}