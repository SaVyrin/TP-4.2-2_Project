package ru.surfstudio.standard.i_user

import ru.surfstudio.standard.domain.entity.UserInfo

interface UserStorage {

    var currentUser: UserInfo?

    fun clear()
}