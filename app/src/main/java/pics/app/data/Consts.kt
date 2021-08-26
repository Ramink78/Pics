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
const val PROGRESS_KEY = "Progress"
const val DOWNLOAD_WORKER_TAG="WorkerTag"
const val KEY_IMAGE_URL = "ImageUrl"
const val KEY_IMAGE_WIDTH = "ImageWidth"
const val KEY_IMAGE_HEIGHT = "ImageHeight"
const val KEY_IMAGE_THUMBNAIL_URL = "ThumbnailUrl"
const val KEY_IMAGE_COLOR = "ImageColor"
const val KEY_IMAGE_ID = "ImageId"
const val KEY_IMAGE_URI = "ImageUri"


//Downloading Notification
@JvmField
val NOTIFICATION_DOWNLOAD_CHANNEL_NAME: CharSequence =
    "Download Image"
const val NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications when downloading photo started"

const val NOTIFICATION_CHANNEL_ID = "pics.app"
const val NOTIFICATION_ID = 1
