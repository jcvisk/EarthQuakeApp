package com.iunis.proyectos.earthquakeapp.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface EqApiService {

    /*
    @GET("all_hour.geojson")
    suspend fun getLastHourEarthquakes():String*/

    @GET("all_hour.geojson")
    suspend fun getLastHourEarthquakes(): EqJsonResponse


}

 val retrofit = Retrofit.Builder()
     .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
     .addConverterFactory(MoshiConverterFactory.create())
     .build()

val service= retrofit.create<EqApiService>(EqApiService::class.java)
