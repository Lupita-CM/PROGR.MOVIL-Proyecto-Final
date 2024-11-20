import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = application.applicationContext

    // Propiedades para manejar el idioma
    private val _selectedLanguage = mutableStateOf("Español")
    val selectedLanguage: State<String> = _selectedLanguage

    fun setLanguage(language: String) {
        _selectedLanguage.value = language
        updateLocale(language)
    }

    private fun updateLocale(languageCode: String) {
        try {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = context.resources.configuration
            config.setLocale(locale)

            // Usar createConfigurationContext en lugar de updateConfiguration
            context.createConfigurationContext(config)

            Log.d("SharedViewModel", "Locale updated to: $languageCode")
        } catch (e: Exception) {
            Log.e("SharedViewModel", "Failed to update locale", e)
        }
    }

    // Propiedades para manejar la pantalla seleccionada
    private val _selectedScreen = MutableStateFlow(0)
    val selectedScreen: StateFlow<Int> = _selectedScreen

    private val _isInitialMode = MutableStateFlow(true)
    val isInitialMode: StateFlow<Boolean> = _isInitialMode

    fun selectScreen(screen: Int) {
        _selectedScreen.value = screen
        _isInitialMode.value = false
    }

    // Propiedades para manejar el tema oscuro/claro
    var isDarkTheme = mutableStateOf(false)
        private set

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}
