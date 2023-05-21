package ru.surfstudio.standard.f_pay.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.f_pay.PayUi
import ru.surfstudio.standard.f_pay.R
import ru.surfstudio.standard.f_pay.databinding.ListItemControllerPayBinding
import ru.surfstudio.standard.ui.util.distinctText

class PayController : BindableItemController<PayUi, PayController.Holder>() {

    override fun getItemId(data: PayUi): Any = data.ipu.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<PayUi>(parent, R.layout.list_item_controller_pay) {

        private val binding = ListItemControllerPayBinding.bind(itemView)

        override fun bind(data: PayUi) {
            with(binding) {
                payCurrentValueEdt.distinctText = data.ipu.value
                payTitleTv.text = data.ipu.type
                payPreviousValueTv.text = data.payAmount
            }
        }
    }
}