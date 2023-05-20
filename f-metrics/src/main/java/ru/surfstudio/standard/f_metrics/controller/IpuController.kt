package ru.surfstudio.standard.f_metrics.controller

import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.domain.entity.Ipu
import ru.surfstudio.standard.f_metrics.R
import ru.surfstudio.standard.f_metrics.databinding.ListItemControllerIpuBinding

/**
 * Контроллер ИПУ
 */
class IpuController(
    private val onIpuChanged: (Ipu) -> Unit
) : BindableItemController<Ipu, IpuController.Holder>() {

    override fun getItemId(data: Ipu): Any = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<Ipu>(parent, R.layout.list_item_controller_ipu) {

        private val binding = ListItemControllerIpuBinding.bind(itemView)

        override fun bind(data: Ipu) {
            with(binding) {
                metricsTitleTv.text = data.type
                metricsCurrentValueEdt.setText(data.value)
                metricsCurrentValueEdt.doOnTextChanged { text, _, _, _ ->
                    onIpuChanged(data.copy(value = text.toString()))
                }
                metricsPreviousValueTv.text = data.value
            }
        }
    }
}
