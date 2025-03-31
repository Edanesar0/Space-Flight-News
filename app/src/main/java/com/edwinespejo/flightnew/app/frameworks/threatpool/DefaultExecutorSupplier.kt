package com.edwinespejo.flightnew.app.frameworks.threatpool

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class DefaultExecutorSupplier private constructor() {
    private var mForBackgroundTasks: ThreadPoolExecutor? = null
    private val backgroundPriorityThreadFactory: ThreadFactory =
        PriorityThreadFactory(android.os.Process.THREAD_PRIORITY_BACKGROUND)

    init {
        initThreadPoolExecutor()
    }

    val threadPoolExecutor: ThreadPoolExecutor?
        get() {
            if (mForBackgroundTasks != null) {
                initThreadPoolExecutor()
            }
            return mForBackgroundTasks
        }

    private fun initThreadPoolExecutor() {
        mForBackgroundTasks = ThreadPoolExecutor(
            NUMBER_OF_THREADS,
            NUMBER_OF_THREADS,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable?>(),
            backgroundPriorityThreadFactory
        )
    }

    companion object {
        private val NUMBER_OF_CORES: Int = Runtime.getRuntime().availableProcessors()
        private val NUMBER_OF_THREADS: Int = NUMBER_OF_CORES * 2
        private const val KEEP_ALIVE_TIME: Long = 60L
        private var sInstance: DefaultExecutorSupplier? = null
        fun getInstance(): DefaultExecutorSupplier {
            return sInstance ?: synchronized(this) {
                sInstance ?: DefaultExecutorSupplier().also {
                    sInstance = it
                }
            }
        }

    }
}
