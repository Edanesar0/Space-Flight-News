package com.edwinespejo.flightnew.app.usecases.news

import com.edwinespejo.flightnew.app.adapter.repository.NewsRepositoryImp
import com.edwinespejo.flightnew.app.core.exceptions.AppException
import com.edwinespejo.flightnew.app.frameworks.connection.CommonHttpSender
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class FlightNewsUseCaseTest {

    @Test
    fun flightNewsUseCaseServerCannotBeEstablished() = runTest {
        val onSuccessCalled = CompletableDeferred<Boolean>()
        val newsRepository = NewsRepositoryImp(mock(CommonHttpSender::class.java))
        val SUT = FlightNewsUseCase(newsRepository)
        SUT.execute(
            {
                onSuccessCalled.complete(false)
            }, {
                assertNotNull(it)
                assertTrue(it is AppException)
                assertTrue(it.message!!.contains("The server is unreachable"))
                onSuccessCalled.complete(true)
            })

        assertTrue(onSuccessCalled.await())
    }

    @Test
    fun flightNewsUseCasePageServerCannotBeEstablished() = runTest {
        val onSuccessCalled = CompletableDeferred<Boolean>()
        val newsRepository = NewsRepositoryImp(mock(CommonHttpSender::class.java))
        val SUT = FlightNewsUseCase(newsRepository)
        SUT.execute(
            "Test ",
            {
                onSuccessCalled.complete(false)
            }, {
                assertNotNull(it)
                assertTrue(it is AppException)
                assertTrue(it.message!!.contains("The server is unreachable"))
                onSuccessCalled.complete(true)
            })

        assertTrue(onSuccessCalled.await())
    }

}