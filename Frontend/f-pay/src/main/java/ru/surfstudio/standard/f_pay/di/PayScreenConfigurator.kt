package ru.surfstudio.standard.f_pay.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.mvi.impls.event.hub.dependency.ScreenEventHubDependency
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinder
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinderDependency
import ru.surfstudio.android.core.mvp.configurator.BindableScreenComponent
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_pay.*
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.FragmentScreenConfigurator
import ru.surfstudio.standard.ui.navigation.routes.PayFragmentRoute
import ru.surfstudio.standard.ui.screen_modules.CustomScreenModule
import ru.surfstudio.standard.ui.screen_modules.FragmentScreenModule

/**
 * Конфигуратор таба поиск [PayFragmentView]
 */
internal class PayScreenConfigurator(arguments: Bundle?) : FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, PayScreenModule::class]
    )
    internal interface PayScreenComponent : BindableScreenComponent<PayFragmentView>

    @Module
    internal class PayScreenModule(route: PayFragmentRoute) :
        CustomScreenModule<PayFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<PayEvent>(screenEventHubDependency, PayEvent::Lifecycle)

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<PayEvent>,
            mw: PayMiddleware,
            sh: PayScreenStateHolder,
            reducer: PayReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerPayScreenConfigurator_PayScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .payScreenModule(PayScreenModule(PayFragmentRoute()))
            .build()
    }
}
