package ru.surfstudio.standard.f_profile

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_profile.ui.UserInfoUi
import ru.surfstudio.standard.f_profile.ui.UserInfoUiCreator
import javax.inject.Inject

internal class ProfileState(
    // TODO внести график в ресайклер
    val screenItems: List<UserInfoUi> = emptyList()
)

/**
 * State Holder [ProfileFragmentView]
 */
@PerScreen
internal class ProfileScreenStateHolder @Inject constructor(
    userInfoUiCreator: UserInfoUiCreator
) : State<ProfileState>(
    ProfileState(
        screenItems = userInfoUiCreator.createUserInfoItems(
            UserInfo(
                id = 12312,
                name = "Дмитрий",
                surname = "Дмитриев",
                address = "г. Челябинск, ул. Комарова, д. 9, кв. 42"
            )
        )
    )
)

/**
 * Reducer [ProfileFragmentView]
 */
@PerScreen
internal class ProfileReducer @Inject constructor(
    dependency: BaseReactorDependency
) : BaseReducer<ProfileEvent, ProfileState>(dependency) {

    override fun reduce(state: ProfileState, event: ProfileEvent): ProfileState {
        return state
    }
}