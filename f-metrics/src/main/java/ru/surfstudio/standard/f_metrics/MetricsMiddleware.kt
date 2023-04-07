package ru.surfstudio.standard.f_metrics

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.rx.extension.observeScreenResult
import ru.surfstudio.android.rx.extension.toObservable
import ru.surfstudio.standard.f_metrics.MetricsEvent.Navigation
import ru.surfstudio.standard.ui.dialog.base.SimpleResult
import ru.surfstudio.standard.ui.dialog.simple.SimpleDialogRoute
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.show
import javax.inject.Inject

@PerScreen
internal class MetricsMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val screenResultObserver: ScreenResultObserver,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<MetricsEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<MetricsEvent>): Observable<out MetricsEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                MetricsEvent.OpenDialog::class eventMapTo { showDialog() },
                observeDialogResult()
            )
        }

    private fun showDialog(): Observable<out MetricsEvent> =
        Navigation().show(createDialogRoute()).toObservable()

    private fun observeDialogResult(): Observable<out MetricsEvent> {
        return screenResultObserver
            .observeScreenResult(createDialogRoute())
            .map {
                MetricsEvent.ShowDialogResult(
                    if (it == SimpleResult.POSITIVE) {
                        "ok tapped"
                    } else {
                        "cancel tapped"
                    }
                )
            }
    }

    private fun createDialogRoute() =
        SimpleDialogRoute(
            dialogId = "simple_dialog",
            message = "Quick brown fox jumps over the lazy dog",
            title = "Simple dialog"
        )
}