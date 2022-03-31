package com.iunis.proyectos.earthquakeapp.main

import androidx.lifecycle.LiveData
import com.iunis.proyectos.earthquakeapp.Earthquake
import com.iunis.proyectos.earthquakeapp.api.EqJsonResponse
import com.iunis.proyectos.earthquakeapp.api.Feature
import com.iunis.proyectos.earthquakeapp.api.service
import com.iunis.proyectos.earthquakeapp.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EqDatabase) {

     suspend fun fetchEarthquake(sortByMagnitude:Boolean):MutableList<Earthquake> {
        return withContext(Dispatchers.IO){

            val eqJsonResponse: EqJsonResponse = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqJsonResponse)

            database.eqDao.instertAll(eqList)

            if (sortByMagnitude){
                database.eqDao.getEarthquakesByMagnitude()
            }else{
                database.eqDao.getEarthquakes()
            }
        }
    }

     fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake>{

        val eqList = mutableListOf<Earthquake>()
        val featureList:List<Feature> = eqJsonResponse.features

        for(feature in featureList){
            val properties = feature.properties
            val id = feature.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry= feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            //Agrea a objeto formateado a mi gusto.
            eqList.add(Earthquake(id,place,magnitude,time,longitude,latitude))

        }

        return eqList

    }


}