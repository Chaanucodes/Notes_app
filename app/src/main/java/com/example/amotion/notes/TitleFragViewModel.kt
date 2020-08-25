package com.example.amotion.notes

import android.app.Application
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amotion.database.MyNotes
import com.example.amotion.database.NotesDatabaseDao
import kotlinx.coroutines.*

class TitleFragViewModel(
    val database: NotesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob  = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = database.getAllNotes()

    private var _intentSharing = MutableLiveData<MyNotes>()
    val intentSharing : LiveData<MyNotes>
        get() = _intentSharing

    private var _navigateToEditing = MutableLiveData<Long>()
    val navigateToEditing : LiveData<Long>
    get() = _navigateToEditing

    private var _navigateForNewNote = MutableLiveData<Boolean>()
    val navigateForNewNote : LiveData<Boolean>
    get() = _navigateForNewNote


    fun deleteOneNote(id : Long){
        uiScope.launch {
            withContext(Dispatchers.IO){
                database.deleteThisNote(id)
            }
        }
    }

    fun shareOneNote(id: Long){
        uiScope.launch {
             _intentSharing.value = withContext(Dispatchers.IO){
                 database.get(id)
            }
        }
    }

    fun navigateForNewNote(){
        _navigateForNewNote.value = true
    }


    fun navigateToEditing(noteID : Long){
        _navigateToEditing.value = noteID
    }

    fun callTheFragment(thisActivity: FragmentActivity?, dialogFragment: DialogFragment) {
        val fragmentManager = thisActivity?.supportFragmentManager

        val transaction = fragmentManager?.beginTransaction()
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        transaction!!
            .add(android.R.id.content, dialogFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        _navigateToEditing.value = null
        _navigateForNewNote.value = null
        Log.i("Cares", "Tell me howsit")
    }

    fun navigatedToEditing(){
        _navigateToEditing.value = null
    }

    fun navigatedToNewNote(){
        _navigateForNewNote.value = false
    }

}