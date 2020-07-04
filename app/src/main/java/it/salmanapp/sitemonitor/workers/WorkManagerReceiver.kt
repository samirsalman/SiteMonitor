package it.salmanapp.sitemonitor.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerReceiver: BroadcastReceiver() {
    lateinit var mWorkManager:WorkManager


    override fun onReceive(context:Context, intent: Intent)
    {
        val workManagerUtils = WorkManagerUtils(context)
        workManagerUtils.createUpdateWork()
    }
}