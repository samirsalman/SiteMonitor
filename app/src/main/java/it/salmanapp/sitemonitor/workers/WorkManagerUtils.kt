package it.salmanapp.sitemonitor.workers

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerUtils(val context: Context){

    fun createUpdateWork() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)


        val interval_value: String? = prefs.getString("intervals_list", "15")


        Log.d("interval_value",interval_value.toString())

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequest.Builder(
            UpdateWorker::class.java,
            interval_value!!.toLong(), TimeUnit.MINUTES
        )
            .addTag("UPDATE_INTERVAL")
            .setConstraints(constraints)
            .setInitialDelay(interval_value.toLong(), TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            "UpdateWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )

    }
}