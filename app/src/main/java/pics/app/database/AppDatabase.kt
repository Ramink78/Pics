package pics.app.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pics.app.data.photo.model.Photo
import javax.inject.Singleton

@Singleton
@Database(entities = [SavedPhoto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPhotosDao(): PhotosDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "pics-database")
                .build()
        }
    }


}