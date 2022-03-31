package com.iunis.proyectos.earthquakeapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "earthquakes")
data class Earthquake(@PrimaryKey val id:String,
                      @ColumnInfo (name = "eq_place") val place:String,
                      val magnitude:Double,
                      val time:Long,
                      val longitude:Double,
                      val latitude:Double)