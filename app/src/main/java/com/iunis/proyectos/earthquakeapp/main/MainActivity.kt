package com.iunis.proyectos.earthquakeapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iunis.proyectos.earthquakeapp.Earthquake
import com.iunis.proyectos.earthquakeapp.R
import com.iunis.proyectos.earthquakeapp.api.ApiResponseStatus
import com.iunis.proyectos.earthquakeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycle.layoutManager = LinearLayoutManager(this)

        //val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this,MainViewFactory(application)).get(MainViewModel::class.java)

        val adapter = EqAdapter()
        binding.eqRecycle.adapter = adapter

        viewModel.eqList.observe(this, Observer {
            eqList ->
            adapter.submitList(eqList)

            handleEmptyView(eqList, binding)


        })

        viewModel.status.observe(this, Observer{
            apiResponseStatus ->
            if (apiResponseStatus == ApiResponseStatus.LOADING){
                binding.loading.visibility = View.VISIBLE
            }else if(apiResponseStatus == ApiResponseStatus.DONE){
                binding.loading.visibility = View.GONE
            }else if(apiResponseStatus == ApiResponseStatus.ERROR){
                binding.loading.visibility = View.GONE
            }
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }

      }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.main_menu_sort_magnitude){
            //hacer accion de ordenar por magnitude
            viewModel.reloadEarthquake(true)
        }else if (itemId == R.id.main_menu_sort_time){
            //Ordenar por default
            viewModel.reloadEarthquake(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleEmptyView(eqList: MutableList<Earthquake>?, binding: ActivityMainBinding) {
        if (eqList != null) {
            if(eqList.isEmpty()){
                binding.eqEmptyView.visibility=View.VISIBLE
            }else{
                binding.eqEmptyView.visibility = View.GONE
            }
        }
    }
}