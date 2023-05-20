package ru.surfstudio.standard.f_metrics

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.ui.provider.resource.ResourceProvider
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.f_metrics.MetricsEvent.*
import ru.surfstudio.standard.ui.mvi.mappers.RequestMappers
import javax.inject.Inject

internal data class MetricsState(
    val ipuRequestUi: RequestUi<List<Ipu>> = RequestUi(),
    val metricsUiItems: List<MetricsUi> = emptyList()
)

/**
 * State Holder [MetricsFragmentView]
 */
@PerScreen
internal class MetricsScreenStateHolder @Inject constructor(
) : State<MetricsState>(MetricsState())

@PerScreen
internal class IpuCommandHolder @Inject constructor() {
    val errorMessage = Command<String>()
    val ipuSendSuccess = Command<String>()
}

/**
 * Reducer [MetricsFragmentView]
 */
@PerScreen
internal class MetricsReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: IpuCommandHolder,
    private val resourceProvider: ResourceProvider
) : BaseReducer<MetricsEvent, MetricsState>(dependency) {

    override fun reduce(state: MetricsState, event: MetricsEvent): MetricsState {
        return when (event) {
            is CurrentIpuRequestEvent -> handleIpuRequestEvent(state, event)
            is SendIpuRequestEvent -> handleSendIpuRequestEvent(state, event)
            is Input.IpuChanged -> handleIpuChanged(state, event)
            else -> state
        }
    }

    private fun handleIpuRequestEvent(
        state: MetricsState,
        event: CurrentIpuRequestEvent
    ): MetricsState {
        val request = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.simple())
            .handleError(RequestMappers.error.noInternet(errorHandler))
            .handleError { _, _, _ ->
                val message = resourceProvider.getString(R.string.default_error_message)
                ch.errorMessage.accept(message)
                true
            }
            .build()

        val ipu = request.data ?: emptyList()
        val metricsUiItems = ipu.map {
            val previousValue = resourceProvider.getString(
                R.string.metrics_previous_text,
                it.value
            )
            MetricsUi(it, previousValue)
        }
        return state.copy(
            ipuRequestUi = request,
            metricsUiItems = metricsUiItems
        )
    }

    private fun handleSendIpuRequestEvent(
        state: MetricsState,
        event: SendIpuRequestEvent
    ): MetricsState {
        RequestMapper.builder(event.request)
            .handleError(RequestMappers.error.noInternet(errorHandler))
            .handleError { _, _, _ ->
                val message = resourceProvider.getString(R.string.default_error_message)
                ch.errorMessage.accept(message)
                true
            }
            .build()

        val isSuccess = event.isSuccess
        if (isSuccess) {
            val successMessage = resourceProvider.getString(R.string.metrics_send_success_text)
            ch.ipuSendSuccess.accept(successMessage)
            val metricsItems = state.metricsUiItems.map {
                val previousValue = resourceProvider.getString(
                    R.string.metrics_previous_text,
                    it.ipu.value
                )
                it.copy(previousValue = previousValue)
            }
            return state.copy(metricsUiItems = metricsItems)
        }
        return state
    }

    private fun handleIpuChanged(state: MetricsState, event: Input.IpuChanged): MetricsState {
        val metricsUiItems = state.metricsUiItems.map {
            if (it.ipu.id == event.metricsUi.ipu.id) {
                MetricsUi(event.metricsUi.ipu, it.previousValue)
            } else {
                it
            }
        }
        return state.copy(metricsUiItems = metricsUiItems)
    }
}