package com.iunis.proyectos.earthquakeapp.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.iunis.proyectos.earthquakeapp.Earthquake
import com.iunis.proyectos.earthquakeapp.api.ApiResponseStatus
import com.iunis.proyectos.earthquakeapp.database.getDabase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application):AndroidViewModel(application) {

    private val database = getDabase(application)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status:LiveData<ApiResponseStatus>
        get() = _status

    //val eqList = repository.eqList
    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList:LiveData<MutableList<Earthquake>>
        get() = _eqList

    init {
        reloadEarthquake(false)
    }

    fun reloadEarthquake(sortMyMagnitude:Boolean){
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                repository.fetchEarthquake(sortMyMagnitude)
                _status.value = ApiResponseStatus.DONE
            }catch (e:UnknownHostException){
                _status.value = ApiResponseStatus.NOT_INTERNET_CONNECTION
                Log.d(TAG, "No hay conexion a internet:  ",e)
            }
        }
    }

}