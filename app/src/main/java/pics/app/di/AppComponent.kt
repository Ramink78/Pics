package pics.app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pics.app.MainActivity
import pics.app.PicsApp
import pics.app.ui.explore.DetailPhoto
import pics.app.ui.collections.CollectionsFragment
import pics.app.ui.collections.PhotosCollection
import pics.app.ui.home.HomeFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ListModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getPhotosCollectionComponent():PhotoCollectionComponent.Factory
    fun inject(mainActivity: MainActivity)
    fun inject(picsApp: PicsApp)
    fun inject(detailPhoto: DetailPhoto)
    fun inject(homeFragment: HomeFragment)
    fun inject(collectionsFragment: CollectionsFragment)


}