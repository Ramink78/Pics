package pics.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pics.app.data.THEME_KEY
import pics.app.data.THEME_LIGHT
import pics.app.data.THEME_NIGHT
import pics.app.data.download.DownloadService
import pics.app.utils.Ui
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @Inject
    lateinit var ui: Ui
    lateinit var theme: String

    @Inject
    lateinit var downloadService: DownloadService

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as PicsApp).appComponent.inject(this)
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            theme = getString(THEME_KEY, THEME_LIGHT)!!
        }
        when (theme) {
            THEME_LIGHT -> ui.setTheme(Ui.Theme.LIGHT)
            THEME_NIGHT -> ui.setTheme(Ui.Theme.NIGHT)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        navController = Navigation.findNavController(this, R.id.host)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
        val job = lifecycleScope.launch {

        }



    }

    suspend fun download(url: String) {
        downloadService.downloadFromUrl(url)

    }



}
