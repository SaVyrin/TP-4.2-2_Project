package ru.surfstudio.standard.f_metrics

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

internal data class MetricsState(
    val message: String = EMPTY_STRING
)

/**
 * State Holder [MetricsFragmentView]
 */
@PerScreen
internal class MetricsScreenStateHolder @Inject constructor(
) : State<MetricsState>(MetricsState())

/**
 * Reducer [MetricsFragmentView]
 */
@PerScreen
internal class MetricsReducer @Inject constructor(
    dependency: BaseReactorDependency
) : BaseReducer<MetricsEvent, MetricsState>(dependency) {

    override fun reduce(state: MetricsState, event: MetricsEvent): MetricsState {
        return when (event) {
            is MetricsEvent.ShowDialogResult -> {
                state.copy(message = event.message)
            }
            else -> state
        }
    }
}