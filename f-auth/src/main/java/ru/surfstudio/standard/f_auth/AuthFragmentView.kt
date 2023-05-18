package ru.surfstudio.standard.f_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.jakewharton.rxbinding2.view.clicks
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.template.f_auth.R
import ru.surfstudio.android.template.f_auth.databinding.FragmentAuthBinding
import ru.surfstudio.standard.f_auth.AuthEvent.Input
import ru.surfstudio.standard.f_auth.di.AuthScreenConfigurator
import ru.surfstudio.standard.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.standard.v_message_controller_top.IconMessageController
import javax.inject.Inject

/**
 * Вью таба поиск
 */
internal class AuthFragmentView : BaseMviFragmentView<AuthState, AuthEvent>(),
    CrossFeatureFragment {

    @Inject
    override lateinit var hub: ScreenEventHub<AuthEvent>

    @Inject
    override lateinit var sh: AuthScreenStateHolder

    @Inject
    lateinit var ch: AuthCommandHolder

    @Inject
    lateinit var messageController: IconMessageController

    private val binding by viewBinding(FragmentAuthBinding::bind)

    override fun createConfigurator() = AuthScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun initViews() {
        initCommands()
        with(binding) {
            authLoginEdt.doOnTextChanged { text, _, _, _ ->
                Input.LoginTextChanged(text.toString()).emit()
            }
            authPasswordEdt.doOnTextChanged { text, _, _, _ ->
                Input.PasswordTextChanged(text.toString()).emit()
            }
            authBtn.clicks().emit(Input.AuthClicked)
        }
    }

    override fun render(state: AuthState) {
    }

    private fun initCommands() {
        ch.errorMessage bindTo ::showMessage
    }

    private fun showMessage(message: String) {
        messageController.show(message)
    }
}