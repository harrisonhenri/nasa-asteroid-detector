package com.udacity.asteroidradar.api.network

import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.api.models.NetworkImageOfDay
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface ImageOfDayService {
    @GET("planetary/apod")
    suspend fun getImageOfDay(@Query("api_key") apiKey:String = API_KEY): NetworkImageOfDay
}

interface AsteroidsListService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsList(@Query("start_date")startDate:String,
                                 @Query("end_date")endDate:String,
                                 @Query("api_key")apiKey: String = API_KEY): String
}

object NasaApi {
    val imageOfDayService = retrofit.create(ImageOfDayService::class.java)
    val asteroidsListService = retrofit.create(AsteroidsListService::class.java)
}