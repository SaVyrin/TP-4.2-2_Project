package ru.surfstudio.standard.f_onboarding

import androidx.core.view.isVisible
import com.jakewharton.rxbinding2.view.clicks
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.standard.f_onboarding.OnboardingEvent.*
import ru.surfstudio.standard.f_onboarding.databinding.ActivityOnboardingBinding
import ru.surfstudio.standard.f_onboarding.di.OnboardingScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviActivityView
import ru.surfstudio.standard.ui.util.performIfChanged
import javax.inject.Inject

/**
 * Вью экрана онбординга
 */
internal class OnboardingActivityView : BaseMviActivityView<OnboardingState, OnboardingEvent>(),
    CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<OnboardingEvent>

    @Inject
    override lateinit var sh: OnboardingStateHolder

    private val binding by viewBinding(ActivityOnboardingBinding::bind) { rootView }

    override fun getContentView(): Int = R.layout.activity_onboarding

    override fun createConfigurator() = OnboardingScreenConfigurator(intent)

    override fun getScreenName(): String = "OnboardingActivityView"

    override fun initViews() {
        binding.onboardingNextBtn.clicks().emit(Input.NextBtnClicked)
        binding.onboardingSkipBtn.clicks().emit(Input.SkipBtnClicked)
    }

    override fun render(state: OnboardingState) {
        with(binding) {
            onboardingTitle.performIfChanged(state.onboardingUi) { onboardingUi ->
                setText(onboardingUi.textRes)
            }

            onboardingMetricsIv.performIfChanged(state.onboardingUi) { onboardingUi ->
                isVisible = onboardingUi is OnboardingUi.Metrics
            }
            onboardingPayIv.performIfChanged(state.onboardingUi) { onboardingUi ->
                isVisible = onboardingUi is OnboardingUi.Pay
            }
            onboardingProfileIv.performIfChanged(state.onboardingUi) { onboardingUi ->
                isVisible = onboardingUi is OnboardingUi.Profile
            }
        }
    }
}
