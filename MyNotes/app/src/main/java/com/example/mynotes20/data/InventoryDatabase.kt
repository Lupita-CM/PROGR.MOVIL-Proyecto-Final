package com.example.mynotes20.data

import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class, Task::class, Media::class], version = 3, exportSchema = false)
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
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notes RENAME TO notes_old")
                database.execSQL("CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL, dateCreate INTEGER NOT NULL)")
                database.execSQL("INSERT INTO notes(id, title, description, dateCreate) SELECT id, title, description, dateCreate FROM notes_old")
                database.execSQL("DROP TABLE notes_old")

                // Migración para la tabla "tasks" (similar a la de "notes")
                database.execSQL("ALTER TABLE tasks RENAME TO tasks_old")
                database.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT NOT NULL, description TEXT NOT NULL, dateComplete INTEGER NOT NULL, time TEXT NOT NULL, repeat INTEGER NOT NULL, dateCreate INTEGER NOT NULL, repeatFrecuency TEXT NOT NULL, duration TEXT NOT NULL)")
                database.execSQL("INSERT INTO tasks(id, title, description, dateComplete, time, repeat, dateCreate, repeatFrecuency, duration) SELECT id, title, description, dateComplete, time, repeat, dateCreate, repeatFrecuency, duration FROM tasks_old")
                database.execSQL("DROP TABLE tasks_old")
                // ... (otras sentencias SQL) ...
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN complete INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}