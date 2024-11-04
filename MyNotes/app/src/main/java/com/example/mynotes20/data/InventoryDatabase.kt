package com.example.mynotes20.data

import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class, Task::class, Media::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun mediaDao(): MediaDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "notes_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}