package com.example.notesapp_cheezy.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp_cheezy.R
import com.example.notesapp_cheezy.adapter.NoteListAdapter
import com.example.notesapp_cheezy.data.Note
import com.example.notesapp_cheezy.databinding.FragmentNotesBinding
import com.example.notesapp_cheezy.vm.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    private  var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var  vm : NoteViewModel
    private lateinit var noteListAdapter: NoteListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       _binding = FragmentNotesBinding.bind(view)

        setUpRecyclerView()

        vm.notes.observe(viewLifecycleOwner, Observer {
            noteListAdapter.submitList(it)
        })



        binding.fabAddNote.setOnClickListener {   // By clicking floating action button we are navigating to AddEditNoteFragment
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditNotesFragment()
            findNavController().navigate(action)
        }

    }

    //Function TO setup our recyclerView
    fun setUpRecyclerView(){
        noteListAdapter = NoteListAdapter()
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())  //We use requirwContext in our fragments as it states that our context is not null
            adapter = noteListAdapter
        }

        noteListAdapter.setOnItemClickListner {
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditNotesFragment(it)
            findNavController().navigate(action)
        }
        setSwipeToDelete()
    }

    private fun setSwipeToDelete(){
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ){
            override fun onMove(                                   //This function is for drag and drop functionality, So we have returned false inside it
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val currentNote = noteListAdapter.currentList[itemPosition]

                vm.deleteNote(currentNote)

                Snackbar.make(requireView(),"Note Deleted Successfully", Snackbar.LENGTH_SHORT)
                    .setAction("Undo"){
                        vm.insertNote(currentNote)
                    }.show()
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.rvNotes)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}