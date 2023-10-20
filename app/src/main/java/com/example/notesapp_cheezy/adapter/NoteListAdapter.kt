package com.example.notesapp_cheezy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp_cheezy.data.Note
import com.example.notesapp_cheezy.databinding.SingleNoteViewBinding

class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallBack()) {

    inner class NoteViewHolder(private val binding: SingleNoteViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListner?.let {
                    it(getItem(adapterPosition))
                }
            }
        }

        fun bind(currentNote: Note) {
            binding.apply {
                tvTitle.text = currentNote.title
                tvDescription.text = currentNote.description
                val lastModifiedString = "Last Modified: ${currentNote.createdDateFormated}"
                tvNoteCreatedDate.text = lastModifiedString
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        //Here we inflate the layout
        val binding = SingleNoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // We bind data to our Single Item
        val currentNote = getItem(position)
        if (currentNote != null) {
            holder.bind(currentNote)               //We have defined this bind function inside our NoteViewHolder class
        }
    }

    private var onItemClickListner: ((Note) -> Unit)? = null


    //This function takes lambda fun as parameter
    fun setOnItemClickListner(listner: (Note) -> Unit) {
        onItemClickListner = listner
    }

    class DiffCallBack: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}