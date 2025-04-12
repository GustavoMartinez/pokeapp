import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.app.pokeapp.util.FavoritesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Clase que maneja la lista de favoritos
 */
class PokemonFavoritesViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    // StateFlow que expone la lista de favoritos
    private val _favorites = MutableStateFlow(FavoritesManager.getFavorites(context))
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    fun toggleFavorite(pokemonName: String) {
        FavoritesManager.toggleFavorite(context, pokemonName)
        _favorites.value = FavoritesManager.getFavorites(context)
    }

    fun isFavorite(pokemonName: String): Boolean {
        return _favorites.value.contains(pokemonName)
    }
}
