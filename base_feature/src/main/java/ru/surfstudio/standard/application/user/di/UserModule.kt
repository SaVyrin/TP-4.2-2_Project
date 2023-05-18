package ru.surfstudio.standard.application.user.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.surfstudio.standard.i_user.DefaultUserStorage
import ru.surfstudio.standard.i_user.UserStorage

@Module
class UserModule {

    @Provides
    @PerApplication
    fun provideUserStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): UserStorage {
        return DefaultUserStorage(
            AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor
        )
    }
}