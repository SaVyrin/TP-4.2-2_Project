package ru.surfstudio.standard.ui.navigation.routes

import java.io.Serializable

/**
 * Типы табов на главном экране MainActivityView
 * TODO переименовать табы и добавить свои при необходимости
 */
enum class MainTabType: Serializable {
    METRICS, // экран передачи показаний
    PAY, // экран оплаты услуг
    PROFILE // экран профиля пользователя
}