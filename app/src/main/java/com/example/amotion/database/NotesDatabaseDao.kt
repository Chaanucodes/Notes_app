package com.example.amotion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDatabaseDao {

    @Insert
    fun insert(note : MyNotes)

    @Update
    fun update(note : MyNotes)

    @Query("SELECT * from my_notes_table where noteId = :key")
    fun get(key : Long) : MyNotes

    @Query("SELECT * from my_notes_table order by noteId DESC")
    fun getAllNotes() : LiveData<List<MyNotes>>

    @Query("DELETE from my_notes_table")
    fun deleteData()

    @Query("DELETE from my_notes_table where noteId = :key")
    fun deleteThisNote(key : Long)

    @Query("SELECT * from my_notes_table where user_text like :data  or title_text like :data")
    fun getFilteredData(data : String) : List<MyNotes>?
}