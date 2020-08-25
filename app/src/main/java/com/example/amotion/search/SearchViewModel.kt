package com.example.amotion.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amotion.database.MyNotes
import com.example.amotion.database.NotesDatabaseDao
import kotlinx.coroutines.*

class SearchViewModel(
    private val dataSource : NotesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob  = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = dataSource.getAllNotes()

    val stringKey = MutableLiveData<String>()

    private var _searchResults = MutableLiveData<List<MyNotes>?>()
    val searchResults : LiveData<List<MyNotes>?>
    get() = _searchResults


    fun searchData(data : String){
        uiScope.launch {
            _searchResults.value = searchInDB("%$data%")
        }
    }

    private suspend fun searchInDB(data: String) : List<MyNotes>?{
        return withContext(Dispatchers.IO){
            dataSource.getFilteredData(data)
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
