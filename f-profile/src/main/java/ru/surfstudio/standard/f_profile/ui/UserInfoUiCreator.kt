package ru.surfstudio.standard.f_profile.ui

import ru.surfstudio.android.core.ui.provider.resource.ResourceProvider
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_profile.R
import ru.surfstudio.standard.f_profile.ui.ProfileUi.*
import javax.inject.Inject


internal class UserInfoUiCreator @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    fun createUserInfoItems(userInfo: UserInfo): List<ProfileUi> {
        return mutableListOf<ProfileUi>().apply {
            addUserStatistics()
            addUserInfo(
                title = resourceProvider.getString(R.string.profile_licevoi_schet_text),
                value = userInfo.id.toString()
            )
            addUserInfo(
                title = resourceProvider.getString(R.string.profile_name_text),
                value = userInfo.name
            )
            addUserInfo(
                title = resourceProvider.getString(R.string.profile_surname_text),
                value = userInfo.surname
            )
            addUserInfo(
                title = resourceProvider.getString(R.string.profile_address_text),
                value = userInfo.address
            )
        }
    }

    private fun MutableList<ProfileUi>.addUserStatistics() {
        add(
            UserStatisticsUi()
        )
    }

    private fun MutableList<ProfileUi>.addUserInfo(title: String, value: String) {
        add(
            UserInfoUi(title, value)
        )
    }
}