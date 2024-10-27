package com.infinity.apps.magnisetesttask.domain.model.core

import android.R.attr.data



sealed class Response<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Response<T>(data)
    class DefaultError<T>(message: String) : Response<T>(null, message)
    class UnauthorizedError<T>(message: String) : Response<T>(null, message)
}