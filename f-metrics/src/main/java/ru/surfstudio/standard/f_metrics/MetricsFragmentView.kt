package ru.surfstudio.standard.f_metrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.standard.f_metrics.databinding.FragmentMetricsBinding
import ru.surfstudio.standard.f_metrics.di.MetricsScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
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

    private val binding by viewBinding(FragmentMetricsBinding::bind)

    override fun createConfigurator() = MetricsScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_metrics, container, false)
    }

    override fun render(state: MetricsState) {
    }

    override fun initViews() {
    }
}