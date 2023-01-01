package com.esoapps.asteroidradarnasa.network.api

import com.esoapps.asteroidradarnasa.model.PictureOfDay
import com.esoapps.asteroidradarnasa.utils.Constants.BASE_URL
import com.esoapps.asteroidradarnasa.utils.Constants.NASA_KEY
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private val client = OkHttpClient.Builder()
    .connectTimeout(100, TimeUnit.SECONDS)
    .readTimeout(100, TimeUnit.SECONDS)

    .addInterceptor { chain ->
        val url = chain
            .request()
            .url()
            .newBuilder()
            //.addQueryParameter("api_key", "API_KEY_VALUE")//WHEN PARAMETER ARE THE SAME
            .build()
        chain.proceed(chain.request().newBuilder().url(url).build())
    }

    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())//STRING
    .addConverterFactory(MoshiConverterFactory.create(moshi))//JSON
    .client(client)
    .baseUrl(BASE_URL)

    //.addConverterFactory(GsonConverterFactory.create())

    .build()

interface RetrofitService {

    //https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY
    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String = NASA_KEY,
        //):String
    ): PictureOfDay?
    //IN CASE I WANT TO CHECK RESPONSE CODE:
//    ):Response<PictureOfDay>

    //https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidFromApi(
        @Query("api_key") apiKey: String = NASA_KEY,

        @Query("start_date") startDate: String,// = "2022-10-24",
        @Query("end_date") endDate: String //= "2022-10-30",
    ): String


}

object Api {
    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}

