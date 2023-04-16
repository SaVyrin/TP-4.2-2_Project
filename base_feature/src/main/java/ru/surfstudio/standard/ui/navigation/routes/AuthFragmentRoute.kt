package ru.surfstudio.standard.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabHeadRoute

class AuthFragmentRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.standard.f_auth.AuthFragmentView"
    }
}