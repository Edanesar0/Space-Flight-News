package com.edwinespejo.flightnew.app.adapter.repository

import com.edwinespejo.flightnew.app.core.exceptions.AppException
import com.edwinespejo.flightnew.app.frameworks.connection.CommonHttpSender
import kotlinx.coroutines.CompletableDeferred
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class NewsRepositoryImpTest {

    @Test
    fun fetchFlightNews() {
        val onSuccessCalled = CompletableDeferred<Boolean>()
        val SUT = NewsRepositoryImp(mock(CommonHttpSender::class.java))
        SUT.fetchFlightNews(
            "url",
            {
                onSuccessCalled.complete(false)
            }, {
                assertNotNull(it)
                assertTrue(it is AppException)
                assertTrue(it.message!!.contains("The server is unreachable"))
                onSuccessCalled.complete(true)
            }
        )

    }

}