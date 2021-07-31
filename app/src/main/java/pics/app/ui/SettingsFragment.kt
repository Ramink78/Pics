package pics.app.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import pics.app.PicsApp
import pics.app.R
import pics.app.data.THEME_KEY
import pics.app.data.THEME_LIGHT
import pics.app.data.THEME_NIGHT
import pics.app.utils.Ui
import pics.app.utils.Ui.Theme.LIGHT
import pics.app.utils.Ui.Theme.NIGHT
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var ui: Ui
    private val themesValues = arrayOf(LIGHT.value, NIGHT.value)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context


        val screen = preferenceManager.createPreferenceScreen(context)
        val displayPreferenceCategory = PreferenceCategory(context).apply {
            title = "Display"
        }
        val listPreference = ListPreference(context).apply {
            entries = context.resources.getStringArray(R.array.theme_entries)
            entryValues = themesValues
            setDefaultValue(THEME_LIGHT)
            key = THEME_KEY
            summary = ui.currentTheme.value
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
            title = context.resources.getString(R.string.theme_title)

        }
        val sharedPreferences = preferenceManager.sharedPreferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        screen.addPreference(displayPreferenceCategory)
        displayPreferenceCategory.addPreference(listPreference)
        preferenceScreen = screen

    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == THEME_KEY) {
            when (sharedPreferences?.getString(key, THEME_LIGHT)) {
                THEME_LIGHT -> ui.setTheme(LIGHT)
                THEME_NIGHT -> ui.setTheme(NIGHT)
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }

}