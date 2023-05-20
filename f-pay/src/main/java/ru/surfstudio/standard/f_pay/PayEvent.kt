package ru.surfstudio.standard.f_pay

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.domain.entity.Payment
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class PayEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, PayEvent()
    data class Lifecycle(override var stage: LifecycleStage) : PayEvent(), LifecycleEvent

    sealed class Input : PayEvent() {
        object PayClicked : Input()
    }

    data class GetCurrentIpuRequestEvent(override val request: Request<List<Ipu>>): RequestEvent<List<Ipu>>, PayEvent()
    data class GetPaymentsRequestEvent(override val request: Request<List<Payment>>): RequestEvent<List<Payment>>, PayEvent()
    data class GetExpectedPaymentRequestEvent(override val request: Request<Int>): RequestEvent<Int>, PayEvent()
    data class PayRequestEvent(override val request: Request<Unit>): RequestEvent<Unit>, PayEvent()
}
