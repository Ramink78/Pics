package pics.app.utils

import androidx.appcompat.app.AppCompatDelegate
import pics.app.data.THEME_LIGHT
import pics.app.data.THEME_NIGHT

object Ui {
    var currentTheme = Theme.LIGHT

    enum class Theme(val value: String) {
        LIGHT(THEME_LIGHT),
        NIGHT(THEME_NIGHT)
    }

    fun setTheme(theme: Theme) {
        when (theme.value) {
            THEME_LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                currentTheme = Theme.LIGHT
            }
            THEME_NIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                currentTheme = Theme.NIGHT
            }
        }

    }

}