package pics.app.network

import okhttp3.OkHttpClient
import okhttp3.Request

object HttpClient {
   private val client by lazy { OkHttpClient() }
    fun makeRequest(request: Request) = client.newCall(request).execute()


}