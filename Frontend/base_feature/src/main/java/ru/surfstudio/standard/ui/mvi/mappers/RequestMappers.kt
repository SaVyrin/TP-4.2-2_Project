package ru.surfstudio.standard.ui.mvi.mappers

import ru.surfstudio.android.core.mvi.ui.mapper.RequestDataMapper
import ru.surfstudio.android.core.mvi.ui.mapper.RequestErrorHandler
import ru.surfstudio.android.core.mvi.ui.mapper.RequestLoadingMapper
import ru.surfstudio.android.core.mvp.binding.rx.request.data.SimpleLoading
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.standard.i_network.network.error.NoInternetException

object RequestMappers {

    val data = DataMappers
    val loading = LoadingMappers
    val error = ErrorMappers

    object DataMappers {

        fun <T> default(): RequestDataMapper<T, T, T> =
            { request, data -> request.getDataOrNull() ?: data }
    }

    object LoadingMappers {
        fun <T1, T2> simple(): RequestLoadingMapper<T1, T2> = { request, _ ->
            SimpleLoading(request.isLoading)
        }
    }

    object ErrorMappers {

        fun <T> noInternet(errorHandler: ErrorHandler): RequestErrorHandler<T> = { error, _, _ ->
            val isNoInternet = error is NoInternetException
            if (isNoInternet) error?.also(errorHandler::handleError)
            isNoInternet
        }
    }
}