package ru.surfstudio.standard.domain.entity

data class UserInfo(
    val id: Int,
    val name: String,
    val surname: String,
    val address: String
) {

    companion object {
        val EMPTY_USER = UserInfo(0, "", "", "")
    }
}