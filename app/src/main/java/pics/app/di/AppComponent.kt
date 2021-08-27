package pics.app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pics.app.MainActivity
import pics.app.PicsApp
import pics.app.ui.SavedFragment
import pics.app.ui.SettingsFragment
import pics.app.ui.collections.CollectionsFragment
import pics.app.ui.detail.DetailPhoto
import pics.app.ui.home.HomeFragment
import pics.app.ui.home.QualityBottomSheet
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class, WorkerModule::class, BinderModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getPhotosCollectionComponent(): PhotoCollectionComponent.Factory
    fun inject(mainActivity: MainActivity)
    fun inject(picsApp: PicsApp)
    fun inject(detailPhoto: DetailPhoto)
    fun inject(homeFragment: HomeFragment)
    fun inject(collectionsFragment: CollectionsFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(savedFragment: SavedFragment)
    fun inject(qualityBottomSheet: QualityBottomSheet) {

    }


}