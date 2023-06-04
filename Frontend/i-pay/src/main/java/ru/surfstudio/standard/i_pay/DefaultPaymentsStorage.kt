package ru.surfstudio.standard.i_pay

import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage
import ru.surfstudio.standard.i_pay.entity.LastPayments

class DefaultPaymentsStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<LastPayments>(
    FileProcessor(cacheDir, KEY_LAST_PAYMENTS, 1),
    namingProcessor,
    LastPayments::class.java
), PaymentsStorage {

    override var lastPayments: LastPayments?
        get() = this.get(KEY_LAST_PAYMENTS)
        set(value) {
            value ?: return
            this.put(KEY_LAST_PAYMENTS, value)
        }

    override fun clear() {
        lastPayments = null
    }

    private companion object {
        const val KEY_LAST_PAYMENTS = "last_payments"
    }
}
