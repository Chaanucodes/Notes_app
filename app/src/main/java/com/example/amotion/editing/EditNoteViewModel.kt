package com.example.amotion.editing

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amotion.database.MyNotes
import com.example.amotion.database.NotesDatabaseDao
import kotlinx.coroutines.*

class EditNoteViewModel(
    val database: NotesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _currentNote = MutableLiveData<MyNotes>()
    val currentNote: LiveData<MyNotes>
        get() = _currentNote

    fun insertInDatabase(note: MyNotes) {
        uiScope.launch {
            insertData(note)
        }
    }

    private suspend fun insertData(note: MyNotes) {
        withContext(Dispatchers.IO) {
            database.insert(note)
        }
    }

    fun updateDatabase(title: String, subTitle: String, text: String) {
        _currentNote.value?.title = title
        _currentNote.value?.subtitle = subTitle
        _currentNote.value?.text = text

        Log.i("ggg", "hhhh : ${_currentNote.value?.title}")
        uiScope.launch {
            updateData(_currentNote.value!!)
        }
    }

    private suspend fun updateData(note: MyNotes) {
        withContext(Dispatchers.IO) {
            database.update(note)

        }
    }

    fun setCurrentNote(notePassed: Long) {
        uiScope.launch {
            _currentNote.value = getNoteFromDatabase(notePassed)
        }
    }

    private suspend fun getNoteFromDatabase(notePassed: Long): MyNotes {
        return withContext(Dispatchers.IO) {
            database.get(notePassed)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}