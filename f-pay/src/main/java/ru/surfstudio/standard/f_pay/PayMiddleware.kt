package ru.surfstudio.standard.f_pay

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_pay.PayEvent.*
import ru.surfstudio.standard.i_ipu.IpuInteractor
import ru.surfstudio.standard.i_pay.PayInteractor
import ru.surfstudio.standard.i_user.UserStorage
import ru.surfstudio.standard.ui.mvi.navigation.base.NavigationMiddleware
import javax.inject.Inject

@PerScreen
internal class PayMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val ipuInteractor: IpuInteractor,
    private val payInteractor: PayInteractor,
    private val userStorage: UserStorage
) : BaseMiddleware<PayEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<PayEvent>): Observable<out PayEvent> =
        transformations(eventStream) {
            addAll(
                onResume() eventMap { getCurrentIpu() },
                Navigation::class decomposeTo navigationMiddleware,
                GetCurrentIpuRequestEvent::class filter { it.isSuccess } eventMap { getPayments() },
            )
        }

    private fun getCurrentIpu(): Observable<out PayEvent> {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        return ipuInteractor.getCurrentIpu(user.id.toString())
            .io()
            .asRequestEvent(::GetCurrentIpuRequestEvent)
    }

    private fun getPayments(): Observable<out PayEvent> {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        return merge(
            payInteractor.getPayments(user.id.toString())
                .io()
                .asRequestEvent(::GetPaymentsRequestEvent),

            payInteractor.getExpectedPayment(user.id.toString())
                .io()
                .asRequestEvent(::GetExpectedPaymentRequestEvent),
        )
    }

    private fun handlePayClicked(): Observable<out PayEvent> {
        val user = userStorage.currentUser ?: UserInfo.EMPTY_USER
        return payInteractor.pay(user.id.toString())
            .io()
            .asRequestEvent(::PayRequestEvent)
    }
}