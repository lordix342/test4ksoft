package com.pride.test4ksoft.viewModel

import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.pride.test4ksoft.repository.DbManager
import com.pride.test4ksoft.repository.NoteClass
import kotlinx.coroutines.launch
import java.util.*

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val dbManager = DbManager(application.applicationContext)
    private val database = FirebaseDatabase.getInstance().getReference("Notes")
    private var noteList: MutableLiveData<ArrayList<NoteClass>> =
        MutableLiveData()

    var noteListForView: MutableLiveData<ArrayList<NoteClass>> = MutableLiveData() // список заміток

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
            noteListForView.value = noteList.value
            dbManager.closeDb()
        }
    }

    fun deleteNote(position: Int) {
        viewModelScope.launch {
            dbManager.openDb()
            dbManager.deleteNote(noteList.value?.get(position)?.id)
            noteList.value = dbManager.sortDb()
            noteListForView.value = noteList.value
            dbManager.closeDb()
        }
    }

    fun insertNewNote() {
        val date = Calendar.getInstance()
        val formating = SimpleDateFormat("yyyy-MM-dd HH:mm")

        viewModelScope.launch {
            dbManager.openDb()
            dbManager.insert("Нова замітка", "", formating.format(date.time).toString())
            noteList.value = dbManager.sortDb()
            noteListForView.value = noteList.value
            dbManager.closeDb()
        }
    }
}