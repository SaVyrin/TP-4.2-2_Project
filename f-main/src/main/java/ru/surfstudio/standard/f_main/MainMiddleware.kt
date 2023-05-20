package ru.surfstudio.standard.f_main

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_main.MainEvent.Navigation
import ru.surfstudio.standard.i_user.UserStorage
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.replace
import ru.surfstudio.standard.ui.navigation.routes.AuthFragmentRoute
import ru.surfstudio.standard.ui.navigation.routes.MainBarRoute
import javax.inject.Inject

@PerScreen
internal class MainMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val userStorage: UserStorage
) : BaseMiddleware<MainEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<MainEvent>): Observable<out MainEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                onCreate() map { handleOnCreate() }
            )
        }

    private fun handleOnCreate(): Navigation {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        return if (user != UserInfo.EMPTY_USER) {
            Navigation().replace(MainBarRoute())
        } else {
            Navigation().replace(AuthFragmentRoute())
        }
    }
}
