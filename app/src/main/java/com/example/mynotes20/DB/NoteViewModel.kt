import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            _notes.clear()
            _notes.addAll(noteDao.getAllNotes())
        }
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
            getNotes() // Actualiza la lista después de insertar
        }
    }

    fun delete(noteId: Int) {
        viewModelScope.launch {
            noteDao.delete(noteId)
            getNotes() // Actualiza la lista después de eliminar
        }
    }
}
