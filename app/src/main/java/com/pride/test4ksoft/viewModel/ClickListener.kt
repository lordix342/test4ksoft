package com.pride.test4ksoft.viewModel

import com.pride.test4ksoft.repository.NoteClass

interface ClickListener {
    fun onClick(note: NoteClass)
}