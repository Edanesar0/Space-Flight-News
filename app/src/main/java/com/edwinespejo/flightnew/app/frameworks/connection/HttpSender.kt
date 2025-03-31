package com.edwinespejo.flightnew.app.frameworks.connection

import com.edwinespejo.flightnew.app.core.entities.HttpHeader
import com.edwinespejo.flightnew.app.core.entities.HttpResponse

interface HttpSender {
    fun postConnectionRequest(
        url: String, headers: List<HttpHeader>?, body: String
    ): HttpResponse?

    fun getConnectionRequest(url: String, headers: List<HttpHeader>?): HttpResponse?
}