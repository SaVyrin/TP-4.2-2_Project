package ru.surfstudio.standard.f_profile.ui

import com.github.mikephil.charting.data.PieData

sealed class ProfileUi {

    /**
     * Элемент с информацией о пользователе
     * */
    data class UserInfoUi(
        val title: String,
        val value: String
    ) : ProfileUi()

    data class UserStatisticsUi(
        val chartData: PieData = PieData()
    ) : ProfileUi()
}

