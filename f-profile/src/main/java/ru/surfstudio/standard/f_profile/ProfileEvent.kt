package ru.surfstudio.standard.f_profile

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.domain.entity.Payment
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class ProfileEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, ProfileEvent()
    data class Lifecycle(override var stage: LifecycleStage) : ProfileEvent(), LifecycleEvent

    sealed class Input : ProfileEvent() {
        object LogoutClicked : Input()
    }

    data class GotCurrentUser(val userInfo: UserInfo) : ProfileEvent()
    data class GotStatistics(val payments: List<Payment>) : ProfileEvent()
    data class GetPaymentsRequestEvent(override val request: Request<List<Payment>>): RequestEvent<List<Payment>>, ProfileEvent()

}
