package ru.surfstudio.standard.f_splash

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import io.reactivex.Completable
import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.android.utilktx.util.SdkUtils
import ru.surfstudio.standard.f_splash.SplashEvent.Navigation
import ru.surfstudio.standard.i_initialization.InitializeAppInteractor
import ru.surfstudio.standard.i_onboarding.OnBoardingStorage
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.*
import ru.surfstudio.standard.ui.navigation.routes.MainActivityRoute
import ru.surfstudio.standard.ui.navigation.routes.OnboardingActivityRoute
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Минимальное время в миллисекундах, в течение которого показывается сплэш
 */
const val TRANSITION_DELAY_MS = 2000L

/**
 * Middleware сплэш экрана [SplashActivityView].
 */
@PerScreen
class SplashMiddleware @Inject constructor(
    baseMiddlewareDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val initializeAppInteractor: InitializeAppInteractor,
    private val onBoardingStorage: OnBoardingStorage
) : BaseMiddleware<SplashEvent>(baseMiddlewareDependency) {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    override fun transform(eventStream: Observable<SplashEvent>) =
        transformations(eventStream) {
            addAll(
                onCreate() react { fetchRemoteConfig() },
                Navigation::class decomposeTo navigationMiddleware,
                mergeInitDelay().map { openNextScreen() }
            )
        }

    private fun fetchRemoteConfig() {
        remoteConfig.fetch(0)
        remoteConfig.activate()
    }

    private fun mergeInitDelay(): Observable<String> {
        val transitionDelay = if (SdkUtils.isAtLeastS()) {
            TRANSITION_DELAY_MS / 4
        } else {
            TRANSITION_DELAY_MS
        }
        val delay = Completable.timer(transitionDelay, TimeUnit.MILLISECONDS)
        val worker = initializeAppInteractor.initialize()
        return Completable.merge(arrayListOf(delay, worker))
            .io()
            .toSingleDefault(EMPTY_STRING)
            .toObservable()
    }

    private fun openNextScreen(): SplashEvent {
        val showOnboarding = remoteConfig.all.entries.find { entry -> entry.key == "show_onboarding" }?.value?.asBoolean() ?: true
        return Navigation().replace(
            if (onBoardingStorage.shouldShowOnBoardingScreen && showOnboarding)
                OnboardingActivityRoute()
            else
                MainActivityRoute()
        )
    }
}