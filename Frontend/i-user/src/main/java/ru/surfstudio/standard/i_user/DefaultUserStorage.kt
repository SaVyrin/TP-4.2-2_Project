package ru.surfstudio.standard.i_user

import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage
import ru.surfstudio.standard.domain.entity.UserInfo

private const val KEY_TOKEN = "TOKEN"

class DefaultUserStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<UserInfo>(
    FileProcessor(cacheDir, KEY_CURRENT_USER, 1),
    namingProcessor,
    UserInfo::class.java
), UserStorage {

    override var currentUser: UserInfo?
        get() = this.get(KEY_CURRENT_USER)
        set(value) {
            value ?: return
            this.put(KEY_CURRENT_USER, value)
        }

    override fun clear() {
        currentUser = null
    }

    private companion object {
        const val KEY_CURRENT_USER = "current_user"
    }
}
