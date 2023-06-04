package ru.surfstudio.standard.f_onboarding

import javax.inject.Inject
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_onboarding.OnboardingEvent.*

internal data class OnboardingState(
    val onboardingUi: OnboardingUi = OnboardingUi.Metrics
)

@PerScreen
internal class OnboardingStateHolder @Inject constructor() :
    State<OnboardingState>(OnboardingState())

@PerScreen
internal class OnboardingReducer @Inject constructor(
    dependency: BaseReactorDependency
) : BaseReducer<OnboardingEvent, OnboardingState>(dependency) {

    override fun reduce(state: OnboardingState, event: OnboardingEvent): OnboardingState {
        return when (event) {
            is Input.NextBtnClicked -> handleNextBtnClicked(state)
            else -> state
        }
    }

    private fun handleNextBtnClicked(state: OnboardingState): OnboardingState {
        return state.copy(onboardingUi = state.onboardingUi.next)
    }
}
