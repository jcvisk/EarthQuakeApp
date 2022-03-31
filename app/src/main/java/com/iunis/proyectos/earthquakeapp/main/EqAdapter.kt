package com.iunis.proyectos.earthquakeapp.main

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iunis.proyectos.earthquakeapp.Earthquake
import com.iunis.proyectos.earthquakeapp.databinding.EqListItemBinding

class EqAdapter: ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }


    lateinit var onItemClickListener: (Earthquake) ->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqViewHolder {

       val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.bind(earthquake)
    }

    inner class EqViewHolder(val binding:EqListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(earthquake: Earthquake){
            binding.eqMagnitudeText.text = earthquake.magnitude.toString()
            binding.eqPlaceText.text = earthquake.place

            binding.root.setOnClickListener {
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    Log.e(TAG,"No se pudo inicializar el onItemClickListener" )
                }
            }



        }
    }


}