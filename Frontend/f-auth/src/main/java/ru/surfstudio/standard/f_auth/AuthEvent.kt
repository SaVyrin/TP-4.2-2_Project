package ru.surfstudio.standard.f_auth

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class AuthEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, AuthEvent()
    data class Lifecycle(override var stage: LifecycleStage) : AuthEvent(), LifecycleEvent

    sealed class Input : AuthEvent() {
        data class LoginTextChanged(val text: String): Input()
        data class PasswordTextChanged(val text: String): Input()
        object AuthClicked : Input()
    }

    data class AuthRequest(override val request: Request<UserInfo>) : RequestEvent<UserInfo>, AuthEvent()
}
