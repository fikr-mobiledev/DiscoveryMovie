package com.fikr.mobiledev.discoverymovie

import android.app.Application
import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.fikr.mobiledev.discoverymovie.di.dbModule
import com.fikr.mobiledev.discoverymovie.di.utilModule
import com.fikr.mobiledev.discoverymovie.di.viewModelModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DiscoveryMovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient().newBuilder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        AndroidNetworking.initialize(this@DiscoveryMovieApp, okHttpClient)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin {
            androidLogger()
            androidContext(this@DiscoveryMovieApp)
            modules(listOf(utilModule, viewModelModule, dbModule))
        }
    }
}
