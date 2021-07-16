package pics.app.di

import dagger.Component
import pics.app.MainActivity
import pics.app.PicsApp
import pics.app.ui.explore.DetailPhoto
import pics.app.ui.explore.ExploreFragment
import pics.app.ui.home.HomeFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {


    fun inject(mainActivity: MainActivity)
    fun inject(picsApp: PicsApp)
    fun inject(detailPhoto: DetailPhoto)
    fun inject(homeFragment: HomeFragment)
    fun inject(exploreFragment: ExploreFragment)


}