package com.example.amotion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyNotes::class], version = 2, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDatabaseDao: NotesDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context) : NotesDatabase{

            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_history_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}