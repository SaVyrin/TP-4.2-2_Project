package ru.surfstudio.standard.f_metrics

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_metrics.MetricsEvent.*
import ru.surfstudio.standard.i_ipu.IpuInteractor
import ru.surfstudio.standard.i_user.UserStorage
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import javax.inject.Inject

@PerScreen
internal class MetricsMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val userStorage: UserStorage,
    private val ipuInteractor: IpuInteractor,
    private val sh: MetricsScreenStateHolder
) : BaseMiddleware<MetricsEvent>(basePresenterDependency) {

    private val state: MetricsState get() = sh.value

    override fun transform(eventStream: Observable<MetricsEvent>): Observable<out MetricsEvent> =
        transformations(eventStream) {
            addAll(
                onCreate() eventMap { handleOnCreate() },
                Navigation::class decomposeTo navigationMiddleware,
                Input.SendIpuClicked::class eventMapTo { handleSendIpuClicked() }
            )
        }

    private fun handleOnCreate(): Observable<out CurrentIpuRequestEvent> {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        return ipuInteractor.getCurrentIpu(user.id.toString())
            .io()
            .asRequestEvent { CurrentIpuRequestEvent(it) }
    }

    private fun handleSendIpuClicked(): Observable<out SendIpuRequestEvent> {
        val ipu = state.metricsUiItems.map { it.ipu }
        return ipuInteractor.sendIpu(ipu)
            .io()
            .asRequestEvent { SendIpuRequestEvent(it) }
    }
}