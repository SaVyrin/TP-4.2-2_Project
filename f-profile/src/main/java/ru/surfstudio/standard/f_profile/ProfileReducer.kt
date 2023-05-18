package ru.surfstudio.standard.f_profile

import android.graphics.Color
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.domain.entity.Payment
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.f_profile.ProfileEvent.*
import ru.surfstudio.standard.f_profile.ui.ProfileUi
import ru.surfstudio.standard.f_profile.ui.UserInfoUiCreator
import ru.surfstudio.standard.ui.util.toPx
import javax.inject.Inject


internal data class ProfileState(
    val screenItems: List<ProfileUi> = emptyList()
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
    dependency: BaseReactorDependency,
    private val userInfoUiCreator: UserInfoUiCreator
) : BaseReducer<ProfileEvent, ProfileState>(dependency) {

    override fun reduce(state: ProfileState, event: ProfileEvent): ProfileState {
        return when (event) {
            is Lifecycle -> handleGetStatistics(state, event)
            is GotCurrentUser -> handleGotCurrentUser(state, event)
            else -> state
        }
    }

    private fun handleGetStatistics(state: ProfileState, event: Lifecycle): ProfileState {
        val chartData = generatePieData(
            listOf(
                Payment("Горячая вода", 300F),
                Payment("Холодная вода", 350F),
                Payment("Свет", 600F),
            )
        )
        val screenItems = state.screenItems.map { screenItem ->
            if (screenItem is ProfileUi.UserStatisticsUi) {
                screenItem.copy(chartData)
            } else {
                screenItem
            }
        }
        return state.copy(screenItems = screenItems)
    }

    private fun generatePieData(payments: List<Payment>): PieData {
        val pieEntries = payments.map { PieEntry(it.value, it.type) }

        val pieDataSet = PieDataSet(pieEntries, "").apply {
            colors = ColorTemplate.COLORFUL_COLORS.toList()
            sliceSpace = 2f
            valueTextColor = Color.WHITE
            valueTextSize = 16.toPx.toFloat()
        }
        return PieData(pieDataSet)
    }

    private fun handleGotCurrentUser(state: ProfileState, event: GotCurrentUser): ProfileState {
        val screenItems = userInfoUiCreator.createUserInfoItems(event.userInfo)
        return state.copy(screenItems = screenItems)
    }
}