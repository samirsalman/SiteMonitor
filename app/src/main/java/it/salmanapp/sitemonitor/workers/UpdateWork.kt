package it.salmanapp.sitemonitor.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import it.salmanapp.sitemonitor.MainActivity
import it.salmanapp.sitemonitor.R
import it.salmanapp.sitemonitor.logic.data.repository.pageRepository.PageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject


const val VERBOSE_NOTIFICATION_CHANNEL_NAME = "SiteMonitor Verbose"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "SiteMonitor Updating"
const val CHANNEL_ID = "SiteMonitor00997"
const val NOTIFICATION_TITLE = "SiteMonitor"
const val NOTIFICATION_ID = 1

class UpdateWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params),KoinComponent {



    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        val appContext = applicationContext
        val repository: PageRepository by inject()
        Log.d("HOME_VIEW_MODEL", "Updating")
        return@withContext try {

            val updates = repository.getUpdates()

            if(updates>0) {
                makeStatusNotification("Ci sono $updates aggiornamenti", appContext)
            }

            Result.success()
        } catch (throwable: Throwable) {
            Log.d("HOME_VIEW_MODEL", throwable.cause.toString())
            Result.failure()
        }
    }

    private fun makeStatusNotification(message: String, context: Context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext,MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(applicationContext,1,
            intent,PendingIntent.FLAG_CANCEL_CURRENT)

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }



}