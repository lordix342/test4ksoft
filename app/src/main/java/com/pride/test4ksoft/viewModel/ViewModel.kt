package com.pride.test4ksoft.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.pride.test4ksoft.repository.DbManager
import com.pride.test4ksoft.repository.NoteClass
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val dbManager = DbManager(application.applicationContext)
    private val database = FirebaseDatabase.getInstance().getReference("Notes")
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    var noteEdit: MutableLiveData<NoteClass> =
        MutableLiveData()            // передача запису для редагування
    var noteList: MutableLiveData<ArrayList<NoteClass>> = MutableLiveData() // список заміток

    fun getEdit(note: NoteClass) {
        viewModelScope.launch {
            noteEdit.value = note
        }
    }

    fun updateFirebase() {
        viewModelScope.launch {
            database.removeValue()
            database.push().setValue(noteList.value)
        }
    }

    fun listNotesDb() {
        viewModelScope.launch {
            dbManager.openDb()
            noteList.value = dbManager.sortDb()
            dbManager.closeDb()
        }
    }

    fun deleteNote(position: Int) {
        viewModelScope.launch {
            dbManager.openDb()
            dbManager.deleteNote(noteList.value?.get(position)?.id)
            noteList.value = dbManager.sortDb()
            dbManager.closeDb()
        }
    }

    fun updateNote(note: NoteClass) {
        viewModelScope.launch {
            dbManager.openDb()
            dbManager.deleteNote(note.id)
            dbManager.insert(note.title, note.description, "$day - $month - $year   $hour:$minute")
            noteList.value = dbManager.sortDb()
            dbManager.closeDb()
        }
    }

    fun insertNewNote() {
        viewModelScope.launch {
            dbManager.openDb()
            dbManager.insert("Нова замітка", "", "$day - $month - $year   $hour:$minute")
            noteList.value = dbManager.sortDb()
            dbManager.closeDb()
        }
    }
}