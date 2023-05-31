package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Forecast
import com.example.weatherapp.databinding.ItemForcastBinding
import java.text.SimpleDateFormat
import java.util.*

class ForcastAdapter(var forcasts:List<Forecast>): RecyclerView.Adapter<ForcastAdapter.ForcastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForcastViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var binding = ItemForcastBinding.inflate(inflater,parent,false)
        return ForcastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForcastViewHolder, position: Int) {
        var forcast = forcasts[position]
        holder.bind(forcast)
    }

    override fun getItemCount(): Int {
        return forcasts.size
    }


    inner class ForcastViewHolder(var itemBinding: ItemForcastBinding) :RecyclerView.ViewHolder(itemBinding.root){
        fun bind(forcast: Forecast) {
            itemBinding.apply {
                lowTempTxt.text = forcast.low.toString()+"°C"
                highTempTxt.text = forcast.high.toString()+"°C"
                titleTxt.text = forcast.text
                dayTxt.text = forcast.day + " " +convertUnixTimestamp(forcast.date)
            }
        }
        private fun convertUnixTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("MM/dd", Locale.getDefault())
            val date = Date(timestamp * 1000)
            return sdf.format(date)
        }

    }

}