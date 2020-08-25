package com.example.amotion.onboarding


import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amotion.R
import com.example.amotion.databinding.OnBoardItemBinding


class OnBoardAdapter : RecyclerView.Adapter<OnBoardAdapter.DataHolder>(){

    var items = listOf<OnBoardingItems>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }


    class DataHolder private constructor(private val binding : OnBoardItemBinding): RecyclerView.ViewHolder(binding.root){

        companion object{
            fun from(parent: ViewGroup) : DataHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OnBoardItemBinding.inflate(layoutInflater, parent, false)

                return DataHolder(binding)
            }
        }

        fun bind(data: OnBoardingItems) {
            binding.item = data
        }
    }
}