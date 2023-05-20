package ru.surfstudio.standard.application.pay.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.i_pay.PayApi

@Module
class PayModule {

    @Provides
    @PerApplication
    internal fun providePayApi(retrofit: Retrofit): PayApi {
        return retrofit.create(PayApi::class.java)
    }
}