package com.mobile.flamy.modulflamy.di.modules

import dagger.Module
import dagger.Provides
import de.greenrobot.event.EventBus
import javax.inject.Singleton

@Module
class BusModule {
    @Provides
    @Singleton
    fun provideBus(): EventBus {
        return EventBus()
    }
}