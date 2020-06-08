package com.unsplash.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {
    val API_KEY="Ov-NmVnr6uWRVKNSOFm4BWIlHIwr_LZH7bW5dzOmdU0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val navView: BottomNavigationView = findViewById(R.id.nav)

        val navController = findNavController(R.id.host)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)
       /*   val request=ServiceBuilder.buildService(API::class.java)
      val call=request.getContent(API_KEY)
      call.enqueue(object : Callback<Data> {
           override fun onResponse(call: Call<Data>, response: Response<Data>) {
              if (response.isSuccessful){

                  val result=response.body()

                  if (result != null) {
                      for (i in 0 until result.size){
                          Log.i("RESPONSE", "url is: "+result[i].urls.regular)
                      }
                  }





                  Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_SHORT).show()


              }
          }
           override fun onFailure(call: Call<Data>, t: Throwable) {
              Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
               Log.i("RESPONSE",t.message)
          }
      })*/



    }

}