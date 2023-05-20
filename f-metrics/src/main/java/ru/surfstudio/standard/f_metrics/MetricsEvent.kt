package ru.surfstudio.standard.f_metrics

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.i_ipu.entity.SendIpuResponse
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class MetricsEvent : Event {
    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, MetricsEvent()
    data class Lifecycle(override var stage: LifecycleStage) : MetricsEvent(), LifecycleEvent

    sealed class Input : MetricsEvent() {
        object SendIpuClicked : Input()
        data class IpuChanged(val metricsUi: MetricsUi) : Input()
    }

    data class CurrentIpuRequestEvent(override val request: Request<List<Ipu>>) : RequestEvent<List<Ipu>>, MetricsEvent()
    data class SendIpuRequestEvent(override val request: Request<SendIpuResponse>) : RequestEvent<SendIpuResponse>, MetricsEvent()
}
