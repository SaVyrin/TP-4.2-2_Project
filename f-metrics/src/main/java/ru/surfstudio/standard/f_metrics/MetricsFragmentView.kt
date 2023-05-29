package ru.surfstudio.standard.f_metrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.standard.f_metrics.MetricsEvent.*
import ru.surfstudio.standard.f_metrics.controller.IpuController
import ru.surfstudio.standard.f_metrics.databinding.FragmentMetricsBinding
import ru.surfstudio.standard.f_metrics.di.MetricsScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.util.performIfChanged
import ru.surfstudio.standard.v_message_controller_top.IconMessageController
import javax.inject.Inject

internal class MetricsFragmentView : BaseMviFragmentView<MetricsState, MetricsEvent>(),
    CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<MetricsEvent>

    @Inject
    override lateinit var sh: MetricsScreenStateHolder

    @Inject
    lateinit var messageController: IconMessageController

    @Inject
    lateinit var ch: IpuCommandHolder

    private val binding by viewBinding(FragmentMetricsBinding::bind)

    private val easyAdapter = EasyAdapter()
    private val ipuController = IpuController { Input.IpuChanged(it).emit() }

    override fun createConfigurator() = MetricsScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_metrics, container, false)
    }

    override fun initViews() {
        initCommands()
        with(binding) {
            metricsSendBtn.setOnClickListener { Input.SendIpuClicked.emit() }
            metricsErrorLayout.errorLoadStateBtn.setOnClickListener { Input.Retry.emit() }

            with(metricsRv) {
                adapter = easyAdapter
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
            }
        }
    }

    override fun render(state: MetricsState) {
        easyAdapter.setData(state.metricsUiItems, ipuController)

        with(binding) {
            metricsRv.performIfChanged(state.showLoading) { isVisible = !it }
            metricsPb.performIfChanged(state.showLoading) { isVisible = it }

            metricsRv.performIfChanged(state.showError) { isVisible = !it }
            metricsErrorLayout.root.performIfChanged(state.showError) { isVisible = it }
        }
    }

    private fun initCommands() {
        ch.errorMessage bindTo ::showMessage
        ch.ipuSendSuccess bindTo { showMessage(it, R.color.colorAccent) }
    }

    private fun showMessage(message: String, colorRes: Int? = null) {
        messageController.show(message, colorRes)
    }
}