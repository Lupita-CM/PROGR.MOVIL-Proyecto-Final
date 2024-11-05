import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    suspend fun getAllNotes(): List<Note>

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)
    abstract fun delete(noteId: Int)
}
