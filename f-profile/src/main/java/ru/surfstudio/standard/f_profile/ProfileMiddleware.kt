package ru.surfstudio.standard.f_profile

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand.Companion.ACTIVITY_NAVIGATION_TAG
import ru.surfstudio.android.rx.extension.toObservable
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_profile.ProfileEvent.*
import ru.surfstudio.standard.i_pay.PayInteractor
import ru.surfstudio.standard.i_pay.PaymentsStorage
import ru.surfstudio.standard.i_user.UserStorage
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.standard.ui.mvi.navigation.extension.replaceHard
import ru.surfstudio.standard.ui.navigation.routes.AuthFragmentRoute
import javax.inject.Inject

@PerScreen
internal class ProfileMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val userStorage: UserStorage,
    private val payInteractor: PayInteractor,
    private val paymentsStorage: PaymentsStorage
) : BaseMiddleware<ProfileEvent>(basePresenterDependency) {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun transform(eventStream: Observable<ProfileEvent>): Observable<out ProfileEvent> =
        transformations(eventStream) {
            addAll(
                onViewRecreate() eventMap { handleOnCreate() },
                Navigation::class decomposeTo navigationMiddleware,
                Input.LogoutClicked::class mapTo { handleLogout() },
                GetPaymentsRequestEvent::class filter { it.isSuccess } map { getStatistics() }
            )
        }

    private fun handleOnCreate(): Observable<out ProfileEvent> {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        return merge(
            GotCurrentUser(userStorage.currentUser ?: UserInfo.EMPTY_USER).toObservable(),
            GotStatistics(paymentsStorage.lastPayments?.payments ?: emptyList()).toObservable(),
            payInteractor.getPayments(user.id.toString())
                .io()
                .asRequestEvent(::GetPaymentsRequestEvent),
        )
    }

    private fun handleLogout(): Navigation {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        val bundle = Bundle().apply {
            putString("user_id", user.id.toString())
        }
        firebaseAnalytics.logEvent("logout", bundle)

        userStorage.currentUser = UserInfo.EMPTY_USER
        return Navigation().replaceHard(
            route = AuthFragmentRoute(),
            sourceTag = ACTIVITY_NAVIGATION_TAG
        )
    }

    private fun getStatistics(): GotStatistics {
        return GotStatistics(paymentsStorage.lastPayments?.payments ?: emptyList())
    }
}