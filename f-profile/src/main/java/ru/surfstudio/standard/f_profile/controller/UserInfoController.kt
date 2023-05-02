package ru.surfstudio.standard.f_profile.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.f_profile.R
import ru.surfstudio.standard.f_profile.databinding.ListItemControllerUserInfoBinding
import ru.surfstudio.standard.f_profile.ui.ProfileUi.*

/**
 * Контроллер информации о пользователе
 */
class UserInfoController() : BindableItemController<UserInfoUi, UserInfoController.Holder>() {

    override fun getItemId(data: UserInfoUi): Any = data.title

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<UserInfoUi>(parent, R.layout.list_item_controller_user_info) {

        private val binding = ListItemControllerUserInfoBinding.bind(itemView)

        override fun bind(data: UserInfoUi) {
            with(binding) {
                userInfoTitleTv.text = data.title
                userInfoValueTv.text = data.value
            }
        }
    }
}
