package com.pride.test4ksoft.viewModel

import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pride.test4ksoft.repository.DbManager
import com.pride.test4ksoft.repository.NoteClass
import kotlinx.coroutines.launch
import java.util.*

class EditViewModel(application: Application) : AndroidViewModel(application) {
    private val dbManager = DbManager(application.applicationContext)
    private var noteEdit: MutableLiveData<NoteClass> = MutableLiveData()

    var noteEditForView: MutableLiveData<NoteClass> =
        MutableLiveData()            // передача запису для редагування

    fun getEdit(note: NoteClass) {
        viewModelScope.launch {
            noteEdit.value = note
            noteEditForView.value = noteEdit.value
        }
    }

    fun chekForUpdate(title: String, description: String, chekingNote: NoteClass) {
        val date = Calendar.getInstance()
        val formating = SimpleDateFormat("yyyy-MM-dd HH:mm")
        if ((title != chekingNote.title)||(description != chekingNote.description)) {
            viewModelScope.launch {
                dbManager.openDb()
                dbManager.deleteNote(chekingNote.id)
                dbManager.insert(title, description, formating.format(date.time).toString())
                dbManager.closeDb()
            }
        }
    }
}