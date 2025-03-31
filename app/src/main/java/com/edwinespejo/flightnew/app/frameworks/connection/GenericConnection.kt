package com.edwinespejo.flightnew.app.frameworks.connection

import com.edwinespejo.flightnew.app.core.entities.HttpResponse
import com.edwinespejo.flightnew.app.frameworks.threatpool.DefaultExecutorSupplier
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

class GenericConnection : Connection {
    override fun post(
        httpURLConnection: HttpURLConnection?, requestBody: String?
    ): HttpResponse? {
        return request(requestPost(httpURLConnection!!, requestBody))
    }

    override fun get(httpURLConnection: HttpURLConnection?): HttpResponse? {
        return request(requestGet(httpURLConnection!!))
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private fun request(callable: Callable<HttpResponse?>?): HttpResponse? {
        val httpResponseFuture: Future<HttpResponse?> =
            DefaultExecutorSupplier.getInstance().threadPoolExecutor!!.submit(callable)
        DefaultExecutorSupplier.getInstance().threadPoolExecutor!!.shutdown()
        return httpResponseFuture.get()
    }

    private fun requestPost(httpURLConnection: HttpURLConnection, requestBody: String?):
            Callable<HttpResponse?> {
        return Callable {
            httpURLConnection.setRequestMethod("POST")
            httpURLConnection.setDoOutput(true)
            httpURLConnection.setDoInput(true)
            httpURLConnection.setUseCaches(false)
            if (requestBody != null) {
                val os = httpURLConnection.getOutputStream()
                val osw = OutputStreamWriter(os, "UTF-8")
                osw.write(requestBody)
                osw.flush()
                osw.close()
            }

            val httpResponse = HttpResponse()
            processResponse(httpResponse, httpURLConnection)
            httpResponse
        }
    }

    private fun requestGet(httpURLConnection: HttpURLConnection): Callable<HttpResponse?> {
        return Callable {
            httpURLConnection.setRequestMethod("GET")
            httpURLConnection.setUseCaches(false)
            val httpResponse = HttpResponse()
            processResponse(httpResponse, httpURLConnection)
            httpResponse
        }
    }

    private fun processResponse(httpResponse: HttpResponse, httpURLConnection: HttpURLConnection) {
        httpResponse.code = httpURLConnection.getResponseCode()
        if (httpResponse.code >= 200 && httpResponse.code < 300) {
            val inputStream: InputStream =
                BufferedInputStream(httpURLConnection.getInputStream())
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            val result = StringBuilder()
            while ((reader.readLine().also { line = it }) != null) {
                result.append(line)
            }
            val data: String = result.toString()
            inputStream.close()
            httpResponse.data = data.toByteArray()
        }
    }

}