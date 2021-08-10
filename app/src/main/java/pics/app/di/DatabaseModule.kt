package pics.app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pics.app.database.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context)=AppDatabase.getInstance(context)
}