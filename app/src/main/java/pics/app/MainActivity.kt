package pics.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.home.*
import retrofit2.Retrofit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        navController = Navigation.findNavController(this, R.id.host)
        NavigationUI.setupWithNavController(nav, navController)


    }

}
