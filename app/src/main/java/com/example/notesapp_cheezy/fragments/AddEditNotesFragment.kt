package com.example.notesapp_cheezy.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.notesapp_cheezy.R
import com.example.notesapp_cheezy.data.Note
import com.example.notesapp_cheezy.databinding.FragmentAddEditNoteBinding
import com.example.notesapp_cheezy.vm.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditNotesFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null         //Non null type
    private val binding get() = _binding!!

    @Inject
    lateinit var vm: NoteViewModel
    private val args: AddEditNotesFragmentArgs by navArgs()

    private var currentNote: Note? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEditNoteBinding.bind(view)

        currentNote =
            args.note       // args.note is the note which is passed to this AddEditNoteFragment

        currentNote?.let { note ->           // the code in this block will only be executed only if current note is not null
            binding.apply {
                edtTitle.setText(note.title)
                edtDescription.setText(note.description)
                edtDescription.requestFocus()

                val lastModifiedString = "Last Modified: ${note.createdDateFormated}"
                tvLastModified.setText(lastModifiedString)
                tvLastModified.visibility = View.VISIBLE
            }
        }
    }

    private fun saveNote(){
        binding.apply {
            val title = edtTitle.text.toString()
            val description = edtDescription.text.toString()

  // 1)  clicked on + and did not add title or description, then we will just return
            if (title.isEmpty() || description.isEmpty()){
                return
            }

  // 2) if user clicks on current note and did not make any changes to the current notes title or description
            currentNote?.let {                              // The code in this block will only get executed if current note is not null
                if (title == it.title && description == it.description){   //Here we are checking if user has made any changes to current note or not, if not then we will just return
                    return
                }
            }
  // 3) If both the conditions are not true, then we will create a new note
            val newNote = if (currentNote == null){
                Note(title,description)
            }else{
                currentNote?.apply {
                    this.title = title
                    this.description = description
                    this.created = System.currentTimeMillis()
                }
            }

            if (newNote != null){
                vm.insertNote(newNote)
                Toast.makeText(requireContext(),"Saved", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}