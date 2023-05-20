package ru.surfstudio.standard.application.ipu.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.i_ipu.IpuApi

@Module
class IpuModule {

    @Provides
    @PerApplication
    internal fun provideIpuApi(retrofit: Retrofit): IpuApi {
        return retrofit.create(IpuApi::class.java)
    }
}