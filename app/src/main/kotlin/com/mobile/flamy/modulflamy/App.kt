package com.mobile.flamy.modulflamy

import android.app.Application
import com.mobile.flamy.modulflamy.di.components.ApplicationComponent
import com.mobile.flamy.modulflamy.di.modules.ApiModule
import com.mobile.flamy.modulflamy.di.modules.ContextModule
import com.mobile.flamy.modulflamy.di.components.DaggerApplicationComponent


open class App : Application() {

    companion object {
        @JvmStatic lateinit var mApplicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        createApplicationComponent()
        super.onCreate()
    }

    internal fun createApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .apiModule(ApiModule())
                .contextModule(ContextModule(this))
                .build()
    }
}