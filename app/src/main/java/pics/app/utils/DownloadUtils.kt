package pics.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import pics.app.R
import pics.app.data.*
import pics.app.data.photo.model.Photo


enum class Quality(val title: String) {
    RAW("Ultra Quality"),
    FULL("High Quality"),
    REGULAR("Medium Quality"),
    SMALL("Low Quality")
}

fun showDownloadingNotification(message: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Make a channel if necessary
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = NOTIFICATION_CHANNEL_NAME
        val description = NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }
    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

fun getImageUrl(photo: Photo, quality: Quality): String? {
    return when (quality) {
        Quality.RAW -> photo.urls.raw
        Quality.FULL -> photo.urls.full
        Quality.REGULAR -> photo.urls.regular
        Quality.SMALL -> photo.urls.small
    }

}

