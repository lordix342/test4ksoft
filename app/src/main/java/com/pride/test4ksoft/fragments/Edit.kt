package com.pride.test4ksoft.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pride.test4ksoft.R
import com.pride.test4ksoft.databinding.FragmentEditBinding
import com.pride.test4ksoft.repository.DbManager
import com.pride.test4ksoft.repository.NoteClass
import com.pride.test4ksoft.viewModel.ViewModel


class Edit : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val viewModel: ViewModel by activityViewModels()
    private var noteForEdit = NoteClass("", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //для змін тулбар
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Editing"
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = FragmentEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.noteEdit.observe(viewLifecycleOwner) { note ->
            if (note != null) {
                noteForEdit = note
                binding.edtitle.setText(note.title)
                binding.editTextTextMultiLine.setText(note.description)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.fab_create).isVisible = false
        menu.findItem(R.id.fab_update).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fab_update -> {
                update()
            }
            16908332 -> { //backStack
                findNavController().navigate(R.id.action_edit_to_listnote)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Notes"
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun update() {
        val newTitle = binding.edtitle.text.toString()
        val newDescription = binding.editTextTextMultiLine.text.toString()
        if ((newTitle == noteForEdit.title) && (newDescription == noteForEdit.description)) {
            findNavController().navigate(R.id.action_edit_to_listnote)
        } else {
            noteForEdit.title = newTitle
            noteForEdit.description = newDescription
            viewModel.updateNote(noteForEdit)
            findNavController().navigate(R.id.action_edit_to_listnote)
        }
    }
}