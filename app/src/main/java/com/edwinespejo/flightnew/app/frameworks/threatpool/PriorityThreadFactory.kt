package com.edwinespejo.flightnew.app.frameworks.threatpool

import java.util.concurrent.ThreadFactory

class PriorityThreadFactory(private val mThreadPriority: Int) : ThreadFactory {
    override fun newThread(runnable: Runnable?): Thread {
        val thread = Thread(runnable)
        thread.setPriority(mThreadPriority)
        return thread
    }
}