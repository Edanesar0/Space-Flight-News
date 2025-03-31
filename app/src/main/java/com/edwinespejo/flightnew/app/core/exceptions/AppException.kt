package com.edwinespejo.flightnew.app.core.exceptions

open class AppException: Exception {
    constructor(message: String?) : super(message)

    constructor(cause: Throwable?) : super(cause)
}