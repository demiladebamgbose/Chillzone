package net.androidbootcamp.chillzone

import android.app.Application
import net.androidbootcamp.chillzone.dagger.AppComponent
import net.androidbootcamp.chillzone.dagger.AppModule
import net.androidbootcamp.chillzone.dagger.DaggerAppComponent

class ChillApp : Application() {

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()

        // DaggerAppComponent
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}