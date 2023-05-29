package ru.surfstudio.standard.f_pay

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.ui.provider.resource.ResourceProvider
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.f_pay.PayEvent.*
import ru.surfstudio.standard.ui.mvi.mappers.RequestMappers
import javax.inject.Inject

internal data class PayState(
    val payUiItems: List<PayUi> = emptyList(),
    val ipuRequest: RequestUi<List<Ipu>> = RequestUi(),
    val paymentSummary: Int = 0,
    val paymentSummaryText: String = EMPTY_STRING,
    val expectedPaymentText: String = EMPTY_STRING
) {

    val canPay: Boolean
        get() = paymentSummary > 0

    val showLoading: Boolean
        get() = payUiItems.isEmpty() && ipuRequest.isLoading

    val showError: Boolean
        get() = payUiItems.isEmpty() && ipuRequest.hasError
}

/**
 * State Holder [PayFragmentView]
 */
@PerScreen
internal class PayScreenStateHolder @Inject constructor(
) : State<PayState>(PayState())

@PerScreen
internal class PayCommandHolder @Inject constructor() {
    val errorMessage = Command<String>()
    val paySuccess = Command<String>()
}

/**
 * Reducer [PayFragmentView]
 */
@PerScreen
internal class PayReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val resourceProvider: ResourceProvider,
    private val ch: PayCommandHolder
) : BaseReducer<PayEvent, PayState>(dependency) {

    override fun reduce(state: PayState, event: PayEvent): PayState {
        return when (event) {
            is GetCurrentIpuRequestEvent -> handleGetCurrentIpuRequestEvent(state, event)
            is GetPaymentsRequestEvent -> handleGetPaymentsRequestEvent(state, event)
            is GetExpectedPaymentRequestEvent -> handleGetExpectedPaymentRequestEvent(state, event)
            is PayRequestEvent -> handlePayRequestEvent(state, event)
            else -> state
        }
    }

    private fun handleGetCurrentIpuRequestEvent(
        state: PayState,
        event: GetCurrentIpuRequestEvent
    ): PayState {
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

        request.data?.let { ipu ->
            val payUiItems = ipu.map { item ->
                val savedIpu = state.payUiItems.find {
                    it.ipu.id == item.id
                }
                val savedPayAmount = savedIpu?.payAmount ?: ""
                PayUi(item, savedPayAmount)
            }
            return state.copy(
                ipuRequest = request,
                payUiItems = payUiItems
            )
        }
        return state.copy(ipuRequest = request)
    }

    private fun handleGetPaymentsRequestEvent(
        state: PayState,
        event: GetPaymentsRequestEvent
    ): PayState {
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

        request.data?.let { payments ->
            val payUiItems = state.payUiItems.map { payUi ->
                val payment = payments.find { payment ->
                    payment.type == payUi.ipu.type
                }
                val payAmount = resourceProvider.getString(
                    R.string.pay_single_metric_text,
                    payment?.value ?: 0
                )
                payUi.copy(payAmount = payAmount)
            }
            val paymentSummary = payments.sumOf { it.value }
            val paymentSummaryText = resourceProvider.getString(
                R.string.pay_summary_text,
                paymentSummary
            )
            return state.copy(
                payUiItems = payUiItems,
                paymentSummary = paymentSummary,
                paymentSummaryText = paymentSummaryText
            )
        }
        return state
    }

    private fun handleGetExpectedPaymentRequestEvent(
        state: PayState,
        event: GetExpectedPaymentRequestEvent
    ): PayState {
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

        request.data?.let { expectedValue ->
            val expectedPaymentText = resourceProvider.getString(
                R.string.pay_next_period_text,
                expectedValue
            )
            return state.copy(expectedPaymentText = expectedPaymentText)
        }
        return state
    }

    private fun handlePayRequestEvent(
        state: PayState,
        event: PayRequestEvent
    ): PayState {
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
            val successMessage = resourceProvider.getString(R.string.pay_success_text)
            ch.paySuccess.accept(successMessage)
        }
        return state
    }
}