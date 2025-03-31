package com.edwinespejo.flightnew.app.frameworks.connection

import com.edwinespejo.flightnew.app.core.entities.HttpHeader
import com.edwinespejo.flightnew.app.core.entities.HttpResponse
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutionException
import javax.net.ssl.HttpsURLConnection

class CommonHttpSender(private val genericConnection: GenericConnection) : HttpSender {

    override fun postConnectionRequest(
        url: String, headers: List<HttpHeader>?, body: String
    ): HttpResponse? {
        var httpResponse: HttpResponse?
        try {
            val connectionBuild = connectionBuild(URL(url), "POST", headers)
            httpResponse =
                genericConnection.post(connectionBuild, body)
        } catch (e: InterruptedException) {
            httpResponse = handleException(e)
            Thread.currentThread().interrupt()
        } catch (e: ExecutionException) {
            httpResponse = handleException(e)
        } catch (e: Exception) {
            httpResponse = handleException(e)
        }
        return httpResponse
    }

    override fun getConnectionRequest(url: String, headers: List<HttpHeader>?): HttpResponse? {
        var httpResponse: HttpResponse?
        try {
            val connectionBuild = connectionBuild(URL(url), "GET", headers)
            httpResponse =
                genericConnection.get(connectionBuild)
        } catch (e: InterruptedException) {
            httpResponse = handleException(e)
            Thread.currentThread().interrupt()
        } catch (e: ExecutionException) {
            httpResponse = handleException(e)
        } catch (e: Exception) {
            httpResponse = handleException(e)
        }
        return httpResponse
    }


    private fun handleException(exception: Exception): HttpResponse? {
        val errorMessage = "${exception.message} ${exception.cause}"
        return buildHttpResponse(0, errorMessage)
    }

    private fun buildHttpResponse(httpCode: Int, errorMessage: String?): HttpResponse {
        val response = HttpResponse()
        response.code = httpCode
        response.message = errorMessage
        return response
    }

    private lateinit var httpURLConnection: HttpURLConnection

    @Throws(IOException::class)
    private fun connectionBuild(
        url: URL, method: String, headers: List<HttpHeader>?
    ): HttpURLConnection {
        httpURLConnection = if (url.protocol.equals("https", ignoreCase = true))
            url.openConnection() as HttpsURLConnection else url.openConnection() as HttpURLConnection
        httpURLConnection.setConnectTimeout(20000)
        httpURLConnection.setReadTimeout(20000)
        httpURLConnection.instanceFollowRedirects = false
        httpURLConnection.setRequestMethod(method)
        if (headers != null) for (header in headers) {
            httpURLConnection.setRequestProperty(header.key, header.value)
        }
        return httpURLConnection
    }
}