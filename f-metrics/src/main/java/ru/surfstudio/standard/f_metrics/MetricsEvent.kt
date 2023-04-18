package ru.surfstudio.standard.f_metrics

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.standard.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class MetricsEvent : Event {
    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) : NavCommandsComposition, MetricsEvent()
    data class Lifecycle(override var stage: LifecycleStage) : MetricsEvent(), LifecycleEvent
}
