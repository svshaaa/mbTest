package com.mobile.flamy.modulflamy.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.mobile.flamy.modulflamy.data.AppDatabase
import com.mobile.flamy.modulflamy.data.QuestionDao
import com.mobile.flamy.modulflamy.data.QuestionDaoManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "questions-db").build()

    @Provides
    @Singleton
    fun providesQuestionDao(database: AppDatabase) = database.questionDao()

    @Provides
    @Singleton
    fun providesQuestionDaoManager(service: QuestionDao): QuestionDaoManager {
        return QuestionDaoManager(service)
    }
}