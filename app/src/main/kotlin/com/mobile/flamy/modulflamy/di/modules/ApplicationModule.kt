package com.mobile.flamy.modulflamy.di.modules

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.mobile.flamy.modulflamy.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class))
 class ApplicationModule {

     lateinit internal var application: Application

    @VisibleForTesting
    fun ApplicationModule(app: App) {
        application = app
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}