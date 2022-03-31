package com.iunis.proyectos.earthquakeapp.api

import com.squareup.moshi.Json

class Properties (@Json(name="mag") val mag:Double, @Json(name="place") val place:String, @Json(name="time")  val time:Long){
}