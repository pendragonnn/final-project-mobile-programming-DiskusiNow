package com.dev.final_project_diskusinow.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.os.Handler
import android.os.Looper

class AppExecutors(
    val diskIo: Executor = Executors.newSingleThreadExecutor(),
    val networkIo: Executor = Executors.newFixedThreadPool(3),
    val mainThread: Executor = MainThreadExecutor()
) {
    val diskIoDispatcher: CoroutineDispatcher = diskIo.asCoroutineDispatcher()
    val networkIoDispatcher: CoroutineDispatcher = networkIo.asCoroutineDispatcher()
    val mainThreadDispatcher: CoroutineDispatcher = mainThread.asCoroutineDispatcher()

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
