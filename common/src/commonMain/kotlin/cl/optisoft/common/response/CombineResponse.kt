package cl.optisoft.common.response

import cl.optisoft.common.response.Response
import cl.optisoft.common.error.ErrorI


inline fun <A, B, E : ErrorI, R> combineResponses(
    a: Response<A, E>,
    b: Response<B, E>,
    onSuccess: (A, B) -> R
): Response<R, E> {
    return when {
        a is Response.Error -> Response.Error(a.error)
        b is Response.Error -> Response.Error(b.error)
        a is Response.Loading || b is Response.Loading -> Response.Loading
        a is Response.Idle || b is Response.Idle -> Response.Idle
        a is Response.Success && b is Response.Success -> Response.Success(
            onSuccess(
                a.data,
                b.data
            )
        )

        else -> Response.Idle
    }
}


inline fun <A, B, C, E : ErrorI, R> combineResponses(
    a: Response<A, E>,
    b: Response<B, E>,
    c: Response<C, E>,
    onSuccess: (A, B, C) -> R
): Response<R, E> {
    return when {
        a is Response.Error -> Response.Error(a.error)
        b is Response.Error -> Response.Error(b.error)
        c is Response.Error -> Response.Error(c.error)
        a is Response.Loading || b is Response.Loading || c is Response.Loading -> Response.Loading
        a is Response.Idle || b is Response.Idle || c is Response.Idle -> Response.Idle
        a is Response.Success && b is Response.Success && c is Response.Success ->
            Response.Success(onSuccess(a.data, b.data, c.data))

        else -> Response.Idle
    }
}

inline fun <A, E : ErrorI, R> combineResponses(
    a: Response<A, E>,
    onSuccess: (A) -> R
): Response<R, E> {
    return when {
        a is Response.Error -> Response.Error(a.error)
        a is Response.Loading -> Response.Loading
        a is Response.Idle  -> Response.Idle
        a is Response.Success  ->
            Response.Success(onSuccess(a.data))

        else -> Response.Idle
    }
}