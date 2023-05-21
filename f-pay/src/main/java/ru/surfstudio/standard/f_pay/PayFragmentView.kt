package ru.surfstudio.standard.f_pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.standard.f_pay.PayEvent.Input
import ru.surfstudio.standard.f_pay.controller.PayController
import ru.surfstudio.standard.f_pay.controller.SummaryController
import ru.surfstudio.standard.f_pay.databinding.FragmentPayBinding
import ru.surfstudio.standard.f_pay.di.PayScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.util.performIfChanged
import ru.surfstudio.standard.v_message_controller_top.IconMessageController
import javax.inject.Inject

/**
 * Вью таба поиск
 */
internal class PayFragmentView : BaseMviFragmentView<PayState, PayEvent>(), CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<PayEvent>

    @Inject
    override lateinit var sh: PayScreenStateHolder

    @Inject
    lateinit var messageController: IconMessageController

    @Inject
    lateinit var ch: PayCommandHolder

    private val binding by viewBinding(FragmentPayBinding::bind)

    private val easyAdapter = EasyAdapter()
    private val payController = PayController()
    private val summaryController = SummaryController()

    override fun createConfigurator() = PayScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay, container, false)
    }

    override fun initViews() {
        with(binding) {
            initCommands()
            paySendBtn.setOnClickListener { Input.PayClicked.emit() }

            with(payRv) {
                adapter = easyAdapter
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
            }
        }
    }

    override fun render(state: PayState) {
        with(binding) {
            payNextPeriodTv.performIfChanged(state.expectedPaymentText, TextView::setText)
            paySendBtn.performIfChanged(state.canPay) { canPay ->
                visibility = if (!canPay) View.INVISIBLE else View.VISIBLE
            }
            paySendDisabledBtn.performIfChanged(state.canPay) { canPay ->
                visibility = if (canPay) View.INVISIBLE else View.VISIBLE
            }
        }

        ItemList.create().apply {
            addAll(state.payUiItems, payController)
            add(state.paymentSummaryText, summaryController)
        }.also(easyAdapter::setItems)
    }

    private fun initCommands() {
        ch.errorMessage bindTo ::showMessage
        ch.paySuccess bindTo { showMessage(it, R.color.colorAccent) }
    }

    private fun showMessage(message: String, colorRes: Int? = null) {
        messageController.show(message, colorRes)
    }
}