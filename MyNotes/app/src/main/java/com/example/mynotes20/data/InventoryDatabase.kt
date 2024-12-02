package com.example.mynotes20.data

import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class, Task::class, Media::class, MediaTask::class], version = 6, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun mediaDao(): MediaDao
    abstract fun mediataskDao(): MediaTaskDao
    //abstract fun reminderDao(): RemindersDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "notes_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            db.execSQL("PRAGMA foreign_keys=ON;")
                        }
                    })
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
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crear la tabla "reminders"
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS reminders (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                taskId INTEGER NOT NULL,
                date INTEGER NOT NULL,
                time TEXT NOT NULL,
                FOREIGN KEY (taskId) REFERENCES tasks(id) ON DELETE CASCADE
            )
            """
                )

                // Actualizar la tabla "media"
                database.execSQL("ALTER TABLE media RENAME TO media_old")
                database.execSQL(
                    """
            CREATE TABLE media (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                filePath TEXT NOT NULL,
                mediaType TEXT NOT NULL,
                noteId INTEGER,
                taskId INTEGER,
                FOREIGN KEY (noteId) REFERENCES notes(id) ON DELETE CASCADE,
                FOREIGN KEY (taskId) REFERENCES tasks(id) ON DELETE CASCADE
            )
            """
                )
                database.execSQL("INSERT INTO media(filePath, mediaType, noteId, taskId) SELECT filePath, mediaType, noteId, taskId FROM media_old")
                database.execSQL("DROP TABLE media_old")
            }

        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Crear la tabla "mediaTasks"
                database.execSQL(
                    """
                    CREATE TABLE mediaTasks (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        filePath TEXT NOT NULL,
                        mediaType TEXT NOT NULL,
                        taskId INTEGER NOT NULL, 
                        FOREIGN KEY (taskId) REFERENCES tasks(id) ON DELETE CASCADE
                    )
                    """
                )

                // 2. Copiar los datos de "media" a "mediaTasks" donde taskId no es nulo
                database.execSQL(
                    """
                    INSERT INTO mediaTasks (filePath, mediaType, taskId)
                    SELECT filePath, mediaType, taskId FROM media WHERE taskId IS NOT NULL
                    """
                )

                // 3. Eliminar la columna "taskId" de la tabla "media"
                database.execSQL("ALTER TABLE media RENAME TO media_old")
                database.execSQL(
                    """
                    CREATE TABLE media (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        filePath TEXT NOT NULL,
                        mediaType TEXT NOT NULL,
                        noteId INTEGER NOT NULL, 
                        FOREIGN KEY (noteId) REFERENCES notes(id) ON DELETE CASCADE
                    )
                    """
                )
                database.execSQL(
                    """
                    INSERT INTO media (filePath, mediaType, noteId) 
                    SELECT filePath, mediaType, noteId FROM media_old
                    """
                )
                database.execSQL("DROP TABLE media_old")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE INDEX index_noteId ON media (noteId)")
                database.execSQL("CREATE INDEX index_taskId ON mediaTasks (taskId)")
                //database.execSQL("CREATE INDEX index_taskId ON reminders (taskId)")
            }
        }
    }
}