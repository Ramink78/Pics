package pics.app

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

const val FIRST_PAGE = 1
const val PER_PAGE = 30

@Singleton
class ServiceBuilder @Inject constructor(private val retrofit: Retrofit) {
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }


}
