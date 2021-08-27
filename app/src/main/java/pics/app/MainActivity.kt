package pics.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.home.*
import pics.app.data.THEME_KEY
import pics.app.data.THEME_LIGHT
import pics.app.data.THEME_NIGHT
import pics.app.utils.Ui
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    lateinit var theme: String


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as PicsApp).appComponent.inject(this)
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            theme = getString(THEME_KEY, THEME_NIGHT)!!
        }
        when (theme) {
            THEME_LIGHT -> Ui.setTheme(Ui.Theme.LIGHT)
            THEME_NIGHT -> Ui.setTheme(Ui.Theme.NIGHT)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        navController = Navigation.findNavController(this, R.id.host)
        NavigationUI.setupWithNavController(bottom_navigation, navController)


    }


}
