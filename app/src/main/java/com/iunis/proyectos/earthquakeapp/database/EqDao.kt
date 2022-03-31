package com.iunis.proyectos.earthquakeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.iunis.proyectos.earthquakeapp.Earthquake

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun instertAll(eqList:MutableList<Earthquake>)

    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes():MutableList<Earthquake>

    @Query("SELECT * FROM earthquakes ORDER BY magnitude ASC")
    fun getEarthquakesByMagnitude():MutableList<Earthquake>

    @Update
    fun updateEq(vararg eq:Earthquake)

    @Delete
    fun deleteEq(vararg eq:Earthquake)


}