package ru.surfstudio.standard.f_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.recycler.decorator.Decorator
import ru.surfstudio.android.recycler.decorator.MasterDecorator
import ru.surfstudio.standard.f_profile.controller.UserInfoController
import ru.surfstudio.standard.f_profile.databinding.FragmentProfileBinding
import ru.surfstudio.standard.f_profile.di.ProfileScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.ui.recylcer.SimpleDecor
import ru.surfstudio.standard.ui.util.toPx
import javax.inject.Inject

/**
 * Вью таба профиль
 */
internal class ProfileFragmentView : BaseMviFragmentView<ProfileState, ProfileEvent>(),
    CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<ProfileEvent>

    @Inject
    override lateinit var sh: ProfileScreenStateHolder

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val easyAdapter = EasyAdapter()
    private val userInfoController = UserInfoController()

    override fun createConfigurator() = ProfileScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun initViews() {
        with(binding) {
            with(profileRv) {
                adapter = easyAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(buildDecoration())
            }
        }
    }

    override fun render(state: ProfileState) {
        easyAdapter.setData(state.screenItems, userInfoController)
    }

    private fun buildDecoration(): MasterDecorator {
        val userInfoDecoration = SimpleDecor(
            top = 20.toPx,
            left = 16.toPx,
            right = 16.toPx
        )

        return Decorator.Builder()
            .offset(userInfoController.viewType() to userInfoDecoration)
            .build()
    }
}