package com.example.movieapp

import android.app.Application
import com.airbnb.lottie.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
@HiltAndroidApp
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}