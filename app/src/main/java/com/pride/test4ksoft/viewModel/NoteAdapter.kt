package com.pride.test4ksoft.viewModel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pride.test4ksoft.R
import com.pride.test4ksoft.databinding.NoteLayoutBinding
import com.pride.test4ksoft.repository.NoteClass


class NoteAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes = ArrayList<NoteClass>()

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = NoteLayoutBinding.bind(itemView)
        fun bind(note: NoteClass, clickListener: ClickListener) = with(binding) {
            texttitle.text = note.title
            textdescription.text = note.description
            textdate.text = note.date
            binding.card.setOnClickListener {
                clickListener.onClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(notes[position], clickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(noteslist: ArrayList<NoteClass>) {
        notes.clear()
        notes.addAll(noteslist)
        notifyDataSetChanged()
    }
}