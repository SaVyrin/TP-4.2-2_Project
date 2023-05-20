package ru.surfstudio.standard.f_pay.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.f_pay.R
import ru.surfstudio.standard.f_pay.databinding.ListItemControllerSummaryBinding

class SummaryController : BindableItemController<String, SummaryController.Holder>() {

    override fun getItemId(data: String): Any = data

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<String>(parent, R.layout.list_item_controller_summary) {

        private val binding = ListItemControllerSummaryBinding.bind(itemView)

        override fun bind(data: String) {
            binding.root.text = data
        }
    }
}