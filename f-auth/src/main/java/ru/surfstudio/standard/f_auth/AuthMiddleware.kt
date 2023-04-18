package ru.surfstudio.standard.f_auth

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_auth.AuthEvent.*
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.replace
import ru.surfstudio.standard.ui.navigation.routes.MetricsFragmentRoute
import javax.inject.Inject

@PerScreen
internal class AuthMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<AuthEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<AuthEvent>): Observable<out AuthEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.AuthClicked::class mapTo { navigateToMetricsScreen() }
            )
        }

    private fun navigateToMetricsScreen(): Navigation {
        return Navigation().replace(MetricsFragmentRoute())
    }
}