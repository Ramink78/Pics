package pics.app.data

import android.os.Build
import android.os.Environment
import pics.app.BuildConfig
import java.io.File

//Api
//const val API_KEY = "Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
const val API_KEY="wCW8gJb79HCOtv5KS2Wn6XOMNOmOJthuONlU3_GctPQ"
val isAboveSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
//UI
const val THEME_LIGHT = "Light"
const val THEME_NIGHT = "Night"

//Preferences
const val THEME_KEY = "theme"

//File



//Downloading Notification
@JvmField
val NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Pics Notifications Channel name"
const val NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications when downloading photo start"
@JvmField
val NOTIFICATION_TITLE: CharSequence = "Downloading photo"
const val CHANNEL_ID = "PICS_NOTIFICATION"
const val NOTIFICATION_ID = 1
