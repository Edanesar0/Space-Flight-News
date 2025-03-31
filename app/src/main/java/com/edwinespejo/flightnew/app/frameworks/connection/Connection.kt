package com.edwinespejo.flightnew.app.frameworks.connection

import com.edwinespejo.flightnew.app.core.entities.HttpResponse
import java.net.HttpURLConnection
import java.util.concurrent.ExecutionException

interface Connection {
    @Throws(ExecutionException::class, InterruptedException::class)
    fun post(httpURLConnection: HttpURLConnection?, requestBody: String?): HttpResponse?

    @Throws(ExecutionException::class, InterruptedException::class)
    fun get(httpURLConnection: HttpURLConnection?): HttpResponse?
}