package com.mobile.flamy.modulflamy.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(context: Context) {
    var mContext: Context = context


    fun ContextModule(context: Context) {
        mContext = context
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return mContext
    }
}