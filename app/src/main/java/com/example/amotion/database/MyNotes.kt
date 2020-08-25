package com.example.amotion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_notes_table")
data class MyNotes(

    @PrimaryKey(autoGenerate = true)
    var noteId : Long = 0L,

    @ColumnInfo(name = "title_text")
    var title : String = "",

    @ColumnInfo(name = "subtitle_text")
    var subtitle: String = "",

    @ColumnInfo(name = "user_text")
    var text: String = "",

    @ColumnInfo(name = "time_of_creation")
    var timeStamp : Long = System.currentTimeMillis()
)