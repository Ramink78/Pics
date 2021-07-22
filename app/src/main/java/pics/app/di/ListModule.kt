package pics.app.di

import dagger.Module
import dagger.Provides
import pics.app.ui.explore.DetailPhoto
import javax.inject.Singleton

@Module
class ListModule {
    @Provides
    fun provideList()= ArrayList<DetailPhoto.Row>()

}