package pics.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pics.app.data.THEME_KEY
import pics.app.data.THEME_LIGHT
import pics.app.data.THEME_NIGHT
import pics.app.utils.PICS_DIR
import pics.app.utils.Ui
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    @Inject
    lateinit var ui: Ui
    lateinit var theme: String

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
        // val dir="${Environment.getStorageDirectory().absolutePath}${File.separator}${Environment.DIRECTORY_PICTURES}${File.separator}Pics"
        navController = Navigation.findNavController(this, R.id.host)
        NavigationUI.setupWithNavController(nav, navController)
        val picsFile = File(PICS_DIR)
        if (!picsFile.exists()) {
            picsFile.mkdir()
        }
        Uri.Builder().encodedPath(PICS_DIR).build()

        val uri= Uri.fromFile(picsFile)
        Timber.d("pics dir:$PICS_DIR")


    }



}
