package it.salmanapp.sitemonitor

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.work.*
import it.salmanapp.sitemonitor.di.modules.applicationModule
import it.salmanapp.sitemonitor.di.modules.viewModelModule
import it.salmanapp.sitemonitor.workers.UpdateWorker
import it.salmanapp.sitemonitor.workers.WorkManagerUtils
import org.koin.core.logger.Level
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit
import kotlin.time.Duration


class SiteMonitorApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SiteMonitorApplication)
            modules (
                listOf(applicationModule, viewModelModule)
            )

        }

        val workManagerUtils = WorkManagerUtils(this)

        workManagerUtils.createUpdateWork()
    }
}