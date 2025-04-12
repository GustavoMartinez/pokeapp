import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.pokeapp.util.FavoritesManager
import com.app.pokeapp.util.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Clase que maneja la lista de favoritos
 */
class PokemonFavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FavoritesRepository(application.applicationContext)

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> = _favorites

    init {
        viewModelScope.launch {
            repository.favorites.collect { stored ->
                _favorites.value = stored.toList()
            }
        }
    }

    fun toggleFavorite(name: String) {
        val current = _favorites.value.toMutableSet()
        if (!current.add(name)) {
            current.remove(name)
        }
        _favorites.value = current.toList()

        // Guardar persistente
        viewModelScope.launch {
            repository.saveFavorites(current)
        }
    }
}


