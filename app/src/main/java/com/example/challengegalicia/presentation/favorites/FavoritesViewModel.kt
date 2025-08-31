package com.example.challengegalicia.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengegalicia.data.local.FavoriteUserRepository
import com.example.challengegalicia.data.toFavoriteUserEntity
import com.example.challengegalicia.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel encargado de manejar el estado y la lógica
 * de la funcionalidad de usuarios favoritos.
 *
 * Se comunica con el repositorio local (Room) para:
 *  - Obtener la lista de favoritos
 *  - Agregar o eliminar usuarios de favoritos
 *  - Consultar si un usuario específico está marcado como favorito
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteUserRepository
) : ViewModel() {

    // StateFlow que expone el estado actual de la UI (Idle, Loading, Success, Error, etc.)
    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Idle)
    val uiState: StateFlow<FavoritesUiState> = _uiState

    init {
        loadFavorites()
    }

    /**
     * Carga la lista completa de usuarios favoritos desde la base de datos local.
     * Actualiza el estado de la UI a Loading mientras se obtienen los datos,
     * y luego emite Success con la lista recuperada.
     */
    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = FavoritesUiState.Loading
            repository.getFavorites()
                .collect { list ->
                    _uiState.value = FavoritesUiState.Success(list)
                }
        }
    }

    /**
     * Agrega un usuario a la lista de favoritos.
     * - Transforma el UserModel en una entidad de base de datos.
     * - Inserta el usuario en Room a través del repositorio.
     * - Refresca la lista para reflejar el cambio en la UI.
     */
    fun addFavorite(user: UserModel) {
        viewModelScope.launch {
            repository.addFavorite(user.toFavoriteUserEntity())
            loadFavorites()
        }
    }

    /**
     * Elimina un usuario de la lista de favoritos usando su UUID.
     * Luego recarga la lista para reflejar el cambio en la UI.
     */
    fun removeFavorite(uuid: String) {
        viewModelScope.launch {
            repository.removeFavorite(uuid)
            loadFavorites()
        }
    }

    /**
     * Consulta de manera reactiva si un usuario está marcado como favorito.
     * Retorna un Flow<Boolean> para observar el estado en tiempo real.
     */
    fun isFavorite(uuid: String) = repository.isFavorite(uuid)
}
