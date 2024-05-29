package com.dev.final_project_diskusinow.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskId: Executor = Executors.newSingleThreadExecutor()
    val networkID: Executor = Executors.newFixedThreadPool(3)
    val mainThread: Executor = MainThreadExecutor()
    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}