package ru.surfstudio.standard.f_profile.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.f_profile.R
import ru.surfstudio.standard.f_profile.databinding.ListItemControllerUserStatisticsBinding
import ru.surfstudio.standard.f_profile.ui.ProfileUi.UserStatisticsUi

/**
 * Контроллер информации о пользователе
 */
class UserStatisticsController() :
    BindableItemController<UserStatisticsUi, UserStatisticsController.Holder>() {


    override fun getItemId(data: UserStatisticsUi): Any = data

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<UserStatisticsUi>(
        parent,
        R.layout.list_item_controller_user_statistics
    ) {

        private val binding = ListItemControllerUserStatisticsBinding.bind(itemView)

        init {
            binding.profilePieChart.holeRadius = 25f
        }

        override fun bind(data: UserStatisticsUi) {
            binding.profilePieChart.data = data.chartData
        }
    }
}
