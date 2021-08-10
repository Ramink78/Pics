package pics.app.di

import androidx.work.Configuration
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pics.app.ServiceBuilder
import pics.app.data.API_KEY
import pics.app.data.collections.CollectionsApi
import pics.app.data.details.DetailsAPI
import pics.app.data.download.DownloadService
import pics.app.data.photo.PhotoAPI
import pics.app.network.DownloadFactoryDelegation
import pics.app.ui.explore.DetailPhoto
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(subcomponents = [PhotoCollectionComponent::class])
class NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(
                interceptor
            )
            .build()
    }


    @Singleton
    @Provides
    fun providesPhotoApi(retrofit: Retrofit): PhotoAPI {
        return ServiceBuilder(retrofit).buildService(PhotoAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesDetailsPhotoApi(retrofit: Retrofit): DetailsAPI {
        return ServiceBuilder(retrofit).buildService(DetailsAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesCollectionsPhotoApi(retrofit: Retrofit): CollectionsApi {
        return ServiceBuilder(retrofit).buildService(CollectionsApi::class.java)
    }
    @Singleton
    @Provides
    fun providesDownloadService(retrofit: Retrofit): DownloadService {
        return ServiceBuilder(retrofit).buildService(DownloadService::class.java)
    }



    @Singleton
    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID $API_KEY")
                .build()
            it.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    }
    @Singleton
    @Provides
    fun provideWorkManagerConfiguration(
        downloadWorkerDelegation: DownloadFactoryDelegation
    ): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(downloadWorkerDelegation)
            .build()
    }

}