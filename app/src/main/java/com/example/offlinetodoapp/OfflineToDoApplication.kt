package com.example.offlinetodoapp

import android.app.Application
import com.example.offlinetodoapp.data.AppContainer
import com.example.offlinetodoapp.data.AppDataContainer


class OfflineToDoApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}