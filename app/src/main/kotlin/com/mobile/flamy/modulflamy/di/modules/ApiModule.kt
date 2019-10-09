package com.mobile.flamy.modulflamy.di.modules

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mobile.flamy.modulflamy.api.ApiManager
import com.mobile.flamy.modulflamy.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    private val DISK_CACHE_SIZE = 10 * 1024 * 1024

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .baseUrl("https://api.stackexchange.com/2.2/")
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiManager(service: ApiService): ApiManager {
        return ApiManager(service)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(context: Context): OkHttpClient {
        return createOkHttpClient(context)
    }

    private fun createOkHttpClient(context: Context): OkHttpClient {
        val cacheDir = File(context.cacheDir, "https")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE.toLong())
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(createHttpLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}