package ru.surfstudio.standard.f_metrics.di

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
import ru.surfstudio.standard.f_metrics.*
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.FragmentScreenConfigurator
import ru.surfstudio.standard.ui.navigation.routes.MetricsFragmentRoute
import ru.surfstudio.standard.ui.screen_modules.CustomScreenModule
import ru.surfstudio.standard.ui.screen_modules.FragmentScreenModule

/**
 * Конфигуратор таба новости [MetricsFragmentView]
 */
internal class MetricsScreenConfigurator(arguments: Bundle?) :
    FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, MetricsScreenModule::class]
    )
    internal interface MetricsScreenComponent : BindableScreenComponent<MetricsFragmentView>

    @Module
    internal class MetricsScreenModule(route: MetricsFragmentRoute) :
        CustomScreenModule<MetricsFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<MetricsEvent>(screenEventHubDependency, MetricsEvent::Lifecycle)

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<MetricsEvent>,
            mw: MetricsMiddleware,
            sh: MetricsScreenStateHolder,
            reducer: MetricsReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerMetricsScreenConfigurator_MetricsScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .metricsScreenModule(MetricsScreenModule(MetricsFragmentRoute()))
            .build()
    }
}
