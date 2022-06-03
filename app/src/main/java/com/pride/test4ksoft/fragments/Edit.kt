package com.pride.test4ksoft.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.pride.test4ksoft.R
import com.pride.test4ksoft.databinding.FragmentEditBinding
import com.pride.test4ksoft.repository.NoteClass
import com.pride.test4ksoft.viewModel.EditViewModel


class Edit : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val editViewModel: EditViewModel by activityViewModels()
    private lateinit var editNote : NoteClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //для змін тулбар
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = resources.getString(R.string.edit_toolbar_name)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = FragmentEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editViewModel.noteEditForView.observe(viewLifecycleOwner) { note ->
            if (note != null) {
                editNote = note
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
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? AppCompatActivity)?.supportActionBar?.title = resources.getString(R.string.main_toolbar_name)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun update() {
        val newTitle = binding.edtitle.text.toString()
        val newDescription = binding.editTextTextMultiLine.text.toString()
        editViewModel.chekForUpdate(newTitle,newDescription,editNote)
        Navigation.findNavController(binding.root).popBackStack()

    }
}