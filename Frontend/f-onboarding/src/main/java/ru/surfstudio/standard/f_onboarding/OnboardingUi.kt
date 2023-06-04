package ru.surfstudio.standard.f_onboarding

sealed class OnboardingUi(
    val textRes: Int,
    val next: OnboardingUi
) {

    object Metrics : OnboardingUi(
        textRes = R.string.onboarding_metrics_title,
        next = Pay
    )

    object Pay : OnboardingUi(
        textRes = R.string.onboarding_pay_title,
        next = Profile
    )

    object Profile : OnboardingUi(
        textRes = R.string.onboarding_profile_title,
        next = None
    )

    object None : OnboardingUi(
        textRes = R.string.onboarding_profile_title,
        next = None
    )
}
