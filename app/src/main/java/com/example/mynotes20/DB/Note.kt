import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // Identificador único
    val title: String,  // Título de la nota
    val content: String,  // Contenido de la nota
    val timestamp: Long  // Marca de tiempo
)
