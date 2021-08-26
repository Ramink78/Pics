package pics.app.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import pics.app.R
import pics.app.data.NOTIFICATION_CHANNEL_DESCRIPTION
import pics.app.data.NOTIFICATION_CHANNEL_ID
import pics.app.data.NOTIFICATION_DOWNLOAD_CHANNEL_NAME
import pics.app.data.NOTIFICATION_ID
import pics.app.data.photo.model.Photo


enum class Quality(val title: String) {
    RAW("Ultra Quality"),
    FULL("High Quality"),
    REGULAR("Medium Quality"),
    SMALL("Low Quality")
}

fun showDownloadingNotification(message: String, context: Context, pendingIntent: PendingIntent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel for API 26+
        createChannel(context)

    }
    // Create the notification
    val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_app)
        .setContentTitle(context.resources.getString(R.string.notification_title))
        .setContentText(message)
        .addAction(0, "Cancel", pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // Show the notification
    NotificationManagerCompat.from(context).notify(1, builder.build())
}


fun NotificationCompat.Builder.updateProgressNotification(progress: Int): Notification {
    setProgress(100, progress, false)
    if (progress == 100) {
        //Download completed
        setSmallIcon(android.R.drawable.stat_sys_download_done)
    }
    return this.build()

}

fun getNotificationBuilder(context: Context, fileName: String, pendingIntent: PendingIntent) =
    NotificationCompat.Builder(
        context,
        context.resources.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.mipmap.ic_app)
        .setOngoing(true)
        .setContentTitle(context.resources.getString(R.string.notification_title, fileName))
        .addAction(0, context.resources.getString(R.string.notification_cancel), pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)


fun getImageUrl(photo: Photo, quality: Quality): String? {
    return when (quality) {
        Quality.RAW -> photo.urls.raw
        Quality.FULL -> photo.urls.full
        Quality.REGULAR -> photo.urls.regular
        Quality.SMALL -> photo.urls.small
    }


}

@RequiresApi(Build.VERSION_CODES.O)
fun createChannel(context: Context) {
    val name = NOTIFICATION_DOWNLOAD_CHANNEL_NAME
    val description = NOTIFICATION_CHANNEL_DESCRIPTION
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        name,
        importance
    )
    channel.description = description

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

