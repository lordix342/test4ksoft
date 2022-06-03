package com.pride.test4ksoft.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pride.test4ksoft.R
import com.pride.test4ksoft.databinding.FragmentListnoteBinding
import com.pride.test4ksoft.repository.NoteClass
import com.pride.test4ksoft.viewModel.ClickListener
import com.pride.test4ksoft.viewModel.EditViewModel
import com.pride.test4ksoft.viewModel.NoteAdapter
import com.pride.test4ksoft.viewModel.ViewModel


class Listnote : Fragment(), ClickListener {

    private lateinit var binding: FragmentListnoteBinding
    private var adapter = NoteAdapter(this)
    private val viewModel: ViewModel by activityViewModels()
    private val editViewModel: EditViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = resources.getString(R.string.main_toolbar_name)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListnoteBinding.inflate(inflater)
        notelist()


        return binding.root
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fab_create) viewModel.insertNewNote()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.fab_update).isVisible = false
        menu.findItem(R.id.fab_create).isVisible = true
    }

    override fun onClick(note: NoteClass) {
        editViewModel.getEdit(note)
        Navigation.findNavController(binding.root).navigate(R.id.action_listnote_to_edit)
    }

    private fun notelist() {
        viewModel.listNotesDb()
        viewModel.noteListForView.observe(viewLifecycleOwner) {
            viewModel.updateFirebase()
            adapter.setData(it)
            binding.rcView.adapter = adapter
            binding.rcView.layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL, false
            )
            swipeNote()
        }
    }

    private fun swipeNote() {
        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(viewHolder.adapterPosition)
            }
        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.rcView)
    }

}