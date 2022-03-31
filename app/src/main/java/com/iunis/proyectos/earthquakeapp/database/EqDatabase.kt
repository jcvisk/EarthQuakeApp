package com.iunis.proyectos.earthquakeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iunis.proyectos.earthquakeapp.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDatabase: RoomDatabase() {
    abstract val eqDao:EqDao
}

private lateinit  var INSTANCE:EqDatabase


fun getDabase(context: Context):EqDatabase{
    synchronized(EqDatabase::class.java){
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EqDatabase::class.java,
                "earthquakedb"
            ).build()
        }

    }

    return INSTANCE

}