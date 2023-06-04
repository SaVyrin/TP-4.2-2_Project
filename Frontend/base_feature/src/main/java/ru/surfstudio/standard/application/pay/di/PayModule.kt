package ru.surfstudio.standard.application.pay.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.surfstudio.standard.i_pay.DefaultPaymentsStorage
import ru.surfstudio.standard.i_pay.PayApi
import ru.surfstudio.standard.i_pay.PaymentsStorage

@Module
class PayModule {

    @Provides
    @PerApplication
    internal fun providePayApi(retrofit: Retrofit): PayApi {
        return retrofit.create(PayApi::class.java)
    }

    @Provides
    @PerApplication
    fun providePaymentsStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): PaymentsStorage {
        return DefaultPaymentsStorage(
            AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor
        )
    }
}