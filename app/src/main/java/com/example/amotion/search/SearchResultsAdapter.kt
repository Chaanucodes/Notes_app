package com.example.amotion.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.amotion.database.MyNotes
import com.example.amotion.databinding.SearchListItemBinding
import com.example.amotion.notes.MyNotesListener

class SearchResultsAdapter(val clickListener : MyNotesListener, val data : MutableLiveData<String>, val lifecycleOwner: LifecycleOwner) : ListAdapter<MyNotes, SearchResultsAdapter.SearchResultsViewHolder>(SearchResultsDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        return SearchResultsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, data, lifecycleOwner)
    }

    class SearchResultsViewHolder private constructor(val binding : SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        companion object{
            fun from(parent: ViewGroup) : SearchResultsViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchListItemBinding.inflate(layoutInflater, parent, false)

                return SearchResultsViewHolder(binding)
            }
        }

        fun bind(
            item: MyNotes,
            clickListener: MyNotesListener,
            data: MutableLiveData<String>,
            lifecycleOwner: LifecycleOwner
        ) {
            binding.searchQuery = item
            binding.clickListener = clickListener
            data.observe(lifecycleOwner, Observer {
                binding.searchKey = it
            })

            binding.executePendingBindings()
        }
    }


}

class SearchResultsDiff : DiffUtil.ItemCallback<MyNotes>(){
    override fun areItemsTheSame(oldItem: MyNotes, newItem: MyNotes): Boolean {
        return oldItem.noteId == oldItem.noteId
    }

    override fun areContentsTheSame(oldItem: MyNotes, newItem: MyNotes): Boolean {
        return oldItem == newItem
    }
}

