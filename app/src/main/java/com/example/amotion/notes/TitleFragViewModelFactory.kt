package com.example.amotion.notes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amotion.database.NotesDatabaseDao

class TitleFragViewModelFactory(
    private val dataSource : NotesDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TitleFragViewModel::class.java)) {
            return TitleFragViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}