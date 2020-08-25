package com.example.amotion.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.amotion.database.MyNotes
import com.example.amotion.databinding.NoteListItemBinding

class NotesListAdapter(
    val clickListener: MyNotesListener,
    val deleteClickListener: MyNotesListener,
    val shareListener : MyNotesListener
) : ListAdapter<MyNotes, NotesListAdapter.NoteViewHolder>(NotesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, deleteClickListener, shareListener)
    }


    class NoteViewHolder private constructor(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteListItemBinding.inflate(layoutInflater, parent, false)

                return NoteViewHolder(binding)
            }
        }

        fun bind(
            item: MyNotes,
            clickListener: MyNotesListener,
            deleteClickListener: MyNotesListener,
            shareListener: MyNotesListener
        ) {
            binding.note = item
            binding.clickListener = clickListener
            binding.deleteListener = deleteClickListener
            binding.shareListener = shareListener
            binding.executePendingBindings()
        }
    }


}

class NotesDiffCallback : DiffUtil.ItemCallback<MyNotes>(){
    override fun areItemsTheSame(oldItem: MyNotes, newItem: MyNotes): Boolean {
        return oldItem.noteId == oldItem.noteId
    }

    override fun areContentsTheSame(oldItem: MyNotes, newItem: MyNotes): Boolean {
        return oldItem == newItem
    }

}

class MyNotesListener(
    var clickListener: (noteId : Long) -> Unit
){
    fun onClick(note : MyNotes){
        clickListener(note.noteId)
    }
}