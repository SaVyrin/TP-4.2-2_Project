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
import ru.surfstudio.standard.f_profile.ProfileEvent.GotCurrentUser
import ru.surfstudio.standard.f_profile.ProfileEvent.GotStatistics
import ru.surfstudio.standard.f_profile.ui.ProfileUi
import ru.surfstudio.standard.f_profile.ui.UserInfoUiCreator
import ru.surfstudio.standard.ui.util.toPx
import javax.inject.Inject

internal data class ProfileState(
    val screenItems: List<ProfileUi> = emptyList(),
    val isFirstLoad: Boolean = true
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
            is GotStatistics -> handleGetStatistics(state, event)
            is GotCurrentUser -> handleGotCurrentUser(state, event)
            else -> state
        }
    }

    private fun handleGetStatistics(state: ProfileState, event: GotStatistics): ProfileState {
        val payments = event.payments.filter { it.value > 0 }
        val chartData = generatePieData(payments, state)
        val showingChart = payments.isNotEmpty()
        val screenItems = state.screenItems.map { screenItem ->
            if (screenItem is ProfileUi.UserStatisticsUi) {
                screenItem.copy(chartData, showingChart)
            } else {
                screenItem
            }
        }
        return state.copy(
            screenItems = screenItems,
            isFirstLoad = false
        )
    }

    private fun generatePieData(payments: List<Payment>, state: ProfileState): PieData {
        val pieEntries = payments.map { PieEntry(it.value.toFloat(), it.type) }

        val textSize = if (state.isFirstLoad) 16.toPx.toFloat() else 16.toFloat()
        val pieDataSet = PieDataSet(pieEntries, "").apply {
            colors = ColorTemplate.COLORFUL_COLORS.toList()
            sliceSpace = 2f
            valueTextColor = Color.BLACK
            valueTextSize = textSize
        }
        return PieData(pieDataSet)
    }

    private fun handleGotCurrentUser(state: ProfileState, event: GotCurrentUser): ProfileState {
        val screenItems = userInfoUiCreator.createUserInfoItems(event.userInfo)
        return state.copy(screenItems = screenItems)
    }
}