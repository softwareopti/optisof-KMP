package cl.optisoft.common.response

import cl.optisoft.common.error.ErrorI

sealed interface Response<out D, out E : ErrorI> {
    data class Success<D>(val data: D) : Response<D, Nothing>
    data class Error<E : ErrorI>(val error: E) : Response<Nothing, E>
    data object Loading : Response<Nothing, Nothing>
    data object Idle : Response<Nothing, Nothing>
}