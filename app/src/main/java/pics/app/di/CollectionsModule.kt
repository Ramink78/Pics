package pics.app.di

import dagger.Module
import dagger.Provides


@Module
class CollectionsModule(private val id: String) {
    @CollectionScope
    @Provides
    fun provideCollectionId():String{
        return id
    }




}