package id.nesd.rcache.app

import android.app.Application
import id.nesd.rcache.RCache

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        RCache.initialize(this)
    }
}
