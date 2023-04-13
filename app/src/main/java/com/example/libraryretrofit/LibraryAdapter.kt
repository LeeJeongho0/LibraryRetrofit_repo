package com.example.libraryretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryretrofit.databinding.ItemViewBinding

class LibraryAdapter (val libraryData:MutableList<LibraryData>):RecyclerView.Adapter<LibraryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = libraryData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.tvGuCode.text = libraryData.get(position).code
        binding.tvLbName.text = libraryData.get(position).name
        binding.tvTelNo.text = libraryData.get(position).phone
        binding.tvAddress.text = libraryData.get(position).address
        binding.tvLatitude.text = libraryData.get(position).latitude
        binding.tvLongitude.text = libraryData.get(position).longitude
    }

    class ViewHolder(val binding: ItemViewBinding):RecyclerView.ViewHolder(binding.root)
}