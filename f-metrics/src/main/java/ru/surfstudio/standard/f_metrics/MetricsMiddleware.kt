package ru.surfstudio.standard.f_metrics

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_metrics.MetricsEvent.Navigation
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import javax.inject.Inject

@PerScreen
internal class MetricsMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<MetricsEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<MetricsEvent>): Observable<out MetricsEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware
            )
        }
}