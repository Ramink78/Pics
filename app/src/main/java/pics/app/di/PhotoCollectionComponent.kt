package pics.app.di

import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import pics.app.ui.collections.PhotosCollection
import javax.inject.Singleton
@CollectionScope
@Subcomponent(modules = [CollectionsModule::class])
interface PhotoCollectionComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(collectionsModule: CollectionsModule):PhotoCollectionComponent
    }

    fun inject(photosCollection: PhotosCollection)
}