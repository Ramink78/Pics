package pics.app.data

import android.os.Build
import android.os.Environment
import java.io.File
const val TITLE_TYPE: Int = 1
const val PHOTO_TYPE: Int = 2

//Api
const val API_KEY = "Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
//const val API_KEY="wCW8gJb79HCOtv5KS2Wn6XOMNOmOJthuONlU3_GctPQ"
val isAboveSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
//UI
const val THEME_LIGHT = "Light"
const val THEME_NIGHT = "Night"

//Preferences
const val THEME_KEY = "theme"

//File

// Download

const val KEY_IMAGE_URL="ImageUrl"
const val KEY_IMAGE_WIDTH="ImageWidth"
const val KEY_IMAGE_HEIGHT="ImageHeight"
const val KEY_IMAGE_THUMBNAIL_URL="ThumbnailUrl"
const val KEY_IMAGE_COLOR="ImageColor"
const val KEY_IMAGE_ID="ImageId"
const val KEY_IMAGE_CREATED_AT="ImageCreatedAt"
const val KEY_IMAGE_URI="ImageCreatedAt"


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
