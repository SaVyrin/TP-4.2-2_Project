package ru.surfstudio.standard.f_metrics.controller

import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.f_metrics.MetricsUi
import ru.surfstudio.standard.f_metrics.R
import ru.surfstudio.standard.f_metrics.databinding.ListItemControllerIpuBinding
import ru.surfstudio.standard.ui.util.distinctText

/**
 * Контроллер ИПУ
 */
class IpuController(
    private val onIpuChanged: (MetricsUi) -> Unit
) : BindableItemController<MetricsUi, IpuController.Holder>() {

    override fun getItemId(data: MetricsUi): Any = data.ipu.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<MetricsUi>(parent, R.layout.list_item_controller_ipu) {

        private val binding = ListItemControllerIpuBinding.bind(itemView)

        override fun bind(data: MetricsUi) {
            with(binding) {
                with(metricsCurrentValueEdt) {
                    distinctText = data.ipu.value
                    setSelection(length())
                    doOnTextChanged { text, _, _, _ ->
                        onIpuChanged(
                            data.copy(
                                ipu = data.ipu.copy(value = text.toString())
                            )
                        )
                    }
                }
                metricsTitleTv.text = data.ipu.type
                metricsPreviousValueTv.text = data.previousValueString
            }
        }
    }
}
