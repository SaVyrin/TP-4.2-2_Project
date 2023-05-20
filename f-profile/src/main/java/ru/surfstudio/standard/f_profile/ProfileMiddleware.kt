package ru.surfstudio.standard.f_profile

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand.Companion.ACTIVITY_NAVIGATION_TAG
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_profile.ProfileEvent.*
import ru.surfstudio.standard.i_user.UserStorage
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.replaceHard
import ru.surfstudio.standard.ui.navigation.routes.AuthFragmentRoute
import javax.inject.Inject

@PerScreen
internal class ProfileMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val userStorage: UserStorage
) : BaseMiddleware<ProfileEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<ProfileEvent>): Observable<out ProfileEvent> =
        transformations(eventStream) {
            addAll(
                onCreate() map { handleOnCreate() },
                Navigation::class decomposeTo navigationMiddleware,
                Input.LogoutClicked::class mapTo { handleLogout() }
            )
        }

    private fun handleOnCreate(): GotCurrentUser {
        return GotCurrentUser(userStorage.currentUser ?: UserInfo.EMPTY_USER)
    }

    private fun handleLogout(): Navigation {
        userStorage.currentUser = UserInfo.EMPTY_USER
        return Navigation().replaceHard(
            route = AuthFragmentRoute(),
            sourceTag = ACTIVITY_NAVIGATION_TAG
        )
    }
}